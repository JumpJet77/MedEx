import React from 'react';
import { Row, Col, Form , 
    Button, Container, FormControl
 } from 'bootstrap-4-react'
 import {Link} from 'react-router-dom'
 import MedExApi from '../../medExApi'
import '../user.css'
import DeatailsList from '../detailsList'
export default class Treatment extends React.Component {
    medExApi = new MedExApi()
    constructor(props){
        super(props)
        this.handlerClickAdd = this.handlerClickAdd.bind(this)
        this.handlerChange = this.handlerChange.bind(this)
        this.handlerShowDetails = this.handlerShowDetails.bind(this)
        this.handlerBack = this.handlerBack.bind(this)
        this.getAllTreatmentList = this.getAllTreatmentList.bind(this)
        this.state = {
            data: [],
            addClickBtn: false,
            dateTreatment: '',
            textTreatment: '',
            startDate: '',
            endDate: '',
            textAreaTreatment: '',
            recomendationsTreatment: '',
            childVisible: false,
            filterData: []
        }
    }

    componentDidMount(){
        this.getAllTreatmentList()
    }

    async getAllTreatmentList(){
        let dataAllTreatment = await this.medExApi
            .getAllTreatment(this.props.idUser)

        this.setState({
            data: [...dataAllTreatment]
        })
    }

   
    async handlerClickAdd(){
        const {addClickBtn,dateTreatment, textTreatment, startDate,
        endDate, textAreaTreatment, recomendationsTreatment} = this.state
        let personData
        this.setState({
            addClickBtn: !this.state.addClickBtn
        })
        this.props.data.map((item) => {
            
            item.person.roles = [{
                id: 5,
                role: "ROLE_USER"
            }]

            personData = item.person
        })
        if(addClickBtn && dateTreatment.length > 1 && textTreatment.length > 1){
    
            let dataTreatment = {
                diagnosisDate: dateTreatment,
                diagnosis: textTreatment,
                treatmentStartDate: startDate,
                treatment: textAreaTreatment,
                treatmentFinishDate: endDate,
                recomendations: recomendationsTreatment,
                patient: personData
            }

            let data = JSON.stringify(dataTreatment)
            await this.medExApi.postTreatment(data, this.props.idUser)
            this.getAllTreatmentList()

            this.setState({
                dateTreatment: '',
                textTreatment: '',
                startDate: '',
                endDate: '',
                textAreaTreatment: '',
                childVisible: false,
            })

        }
    }

    handlerChange(e){
        const {id} = e.currentTarget
        this.setState({
            [id]: e.currentTarget.value
        })
    }

    handlerBack(){
        this.setState({
            childVisible: !this.state.childVisible
        })
    }
    
    handlerShowDetails(id){
        
        this.setState(() => {
            let oldData = this.state.data
            let newData = oldData.filter((item) => {
                return item.id == id
            })
            return {
                childVisible: !this.state.childVisible,
                filterData: newData
            }
        })
        
    }

    render() {
        const {data,addClickBtn, dateTreatment, textTreatment, startDate,
        endDate, textAreaTreatment, childVisible, itemId, filterData, recomendationsTreatment} = this.state
        const {role} = this.props

        const formInputs = {
            background: 'white',
        }

        const itemListStyle = {
            color: '#3baeab'
        }

        const deatilsForm = filterData.map((item) =>{
                    return(
                        <div>
                            <div style={{
                                width: '100%',
                                display: 'flex',
                                justifyContent: 'center',
                                alignItems: 'center'
                            }}>
                                <table border="1" style={{
                                    width: '100%'
                                }}>
                                    <tr>
                                        <th>Дата призначення</th>
                                        <th>Діагноз</th>
                                        <th>Початок лікування</th>
                                        <th>Закінчення лікування</th>
                                        <th>Деталі ліквання</th>
                                        <th>Рекомендаці</th>
                                    </tr>
                                    <tr>
                                        <td>{this.medExApi
                                            .pasreDate(item.diagnosisDate)}</td>
                                        <td>{item.diagnosis}</td>
                                        <td>{this.medExApi
                                            .pasreDate(item.treatmentStartDate)}</td>
                                        <td>{this.medExApi
                                            .pasreDate(item.treatmentFinishDate)}</td>
                                        <td style={{
                                            textAlign: 'left',
                                            padding: '15px',
                                        }}>{item.treatment}</td>
                                        <td style={{
                                            textAlign: 'left',
                                            padding: '15px',
                                        }}>{item.recomendations}</td>
                                    </tr>
                                </table>
                            </div>
                            <Button outline primary className="mt10xp"
                                onClick={this.handlerBack}>Назад</Button>
                        </div>
                    )
            })

        const formAdd = () => {
            if(addClickBtn){
                return (
                    <Form className="shadowBox addTreatmentPanel">
                        <Form.Group className="addTreatmentPanelItems" mb="3">
                            <span className="dateTitleForm" display="flex" >Дата призначення лікування:</span>
                            <Form.Input id="dateTreatment" className="text-black field" style={formInputs}
                                        lg mt="2"
                                        required 
                                        value = {dateTreatment}
                                        onChange = {this.handlerChange} 
                                        type="date" 
                                        placeholder="Дата"/>
                            
                            <span className="dateTitleForm" display="flex" >Діагноз:</span>
                            <Form.Input id="textTreatment" className="text-black field" style={formInputs}
                                        lg mt="2"
                                        required 
                                        value = {textTreatment}
                                        onChange = {this.handlerChange} 
                                        type="text" 
                                        placeholder="Назва діагнозу"/>

                            <span className="dateTitleForm" display="flex" >Початок лікування:</span>
                            <Form.Input id="startDate" className="text-black field" style={formInputs}
                                        lg mt="2"
                                        required 
                                        value = {startDate}
                                        onChange = {this.handlerChange} 
                                        type="date" 
                                        placeholder="Дата"/>

                            <span className="dateTitleForm" display="flex" >Завершення лікування:</span>
                            <Form.Input id="endDate" className="text-black field" style={formInputs}
                                        lg mt="2"
                                        required 
                                        value = {endDate}
                                        onChange = {this.handlerChange} 
                                        type="date" 
                                        placeholder="Дата"/>
                            
                            <span className="dateTitleForm" display="flex" >Опис лікування:</span>
                            <textarea rows="5" display="flex" id="textAreaTreatment" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {textAreaTreatment}
                                        onChange = {this.handlerChange} 
                                        placeholder="Опис лікування"/>
                           
                            <span className="dateTitleForm" display="flex" >Рекомендації:</span>
                            <textarea rows="5" display="flex" id="recomendationsTreatment" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {recomendationsTreatment}
                                        onChange = {this.handlerChange} 
                                        placeholder="Рекомендації лікаря"/>

                            <Button outline primary className="mt10xp"
                                onClick={this.handlerClickAdd}>Призначити</Button>
                        </Form.Group>  
                </Form>
            )
            }
        }

        const userForm = () => {
            switch (role) {
                case 'user':
                    return(
                        <div>
                            {childVisible 
                                ? deatilsForm
                                : <DeatailsList  typeList='treatment'  data={this.state.data} onClick={this.handlerShowDetails}/>
                            }
                        </div>
                    )
                    break
                
                case 'nurse':
                    return(
                        <div>
                            {childVisible 
                                ? deatilsForm
                                : <DeatailsList  typeList='treatment'  data={this.state.data} onClick={this.handlerShowDetails}/>
                            }
                        </div>
                    )
                    break

                case 'mainDoctor':
                    return(
                        <div>
                            {childVisible 
                                ? deatilsForm
                                : <DeatailsList  typeList='treatment'  data={this.state.data} onClick={this.handlerShowDetails}/>
                            }
                        </div>
                    )
                    break
            
                case 'doctor':
                        return(
                            <div>
                                <Button outline primary className="btn_add" 
                                    onClick={this.handlerClickAdd}>Призначити лікування</Button>
                                {formAdd()}
                                {childVisible 
                                    ? deatilsForm
                                    : <DeatailsList typeList='treatment' data={this.state.data} onClick={this.handlerShowDetails}/>
                                }
                            </div>
                        )
                    break

                default:
                    break;
            }
    }


        return (

            <div className="main_info">
                <div className="main_info_item">
                        {userForm()}
                </div>
            </div>
        )
    }
}