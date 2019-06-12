import React from 'react'
import { Container, Row, Col, Form , 
    Button
 } from 'bootstrap-4-react'

 import MedExApi from '../../medExApi'

import DeatailsList from '../detailsList'

class DantistExamination extends React.Component {
    medExApi = new MedExApi()
    constructor(props){
        super(props)
        this.handlerClickAdd = this.handlerClickAdd.bind(this)
        this.handlerChange =  this.handlerChange.bind(this)
        this.getAllDantistExamination = this.getAllDantistExamination.bind(this)
        this.handlerBack = this.handlerBack.bind(this)
        this.handlerShowDetails = this.handlerShowDetails.bind(this)
        this.state = {
            data: [],
            addClickBtn: false,
            dantistExaminationDate: '',
            mucosaState: '',
            gumsState: '',
            toothSediment: '',
            diagnosis: '',
            filterData: [],
            childVisible: false
        }
    }

    componentDidMount(){
        this.getAllDantistExamination()
        console.log(this.state.data)
    }

    async getAllDantistExamination(){
        let dataAllDantistExamination = await this.medExApi
            .getAllDantistExamination(this.props.idUser)

        this.setState({
            data: [...dataAllDantistExamination]
        })

    }

    async handlerClickAdd(){
        let personData
        const {dantistExaminationDate, diagnosis, mucosaState, addClickBtn, gumsState, toothSediment} = this.state
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

        if(addClickBtn && dantistExaminationDate.length > 1 && gumsState.length > 1){
            let dataDantistExamination = {
                dantistExaminationDate,
                mucosaState,
                gumsState,
                toothSediment,
                diagnosis,
                patient: personData
            }
            
            let dataNewDantistExamination = JSON.stringify(dataDantistExamination)
            console.log("Dantist" + dataNewDantistExamination)
            await this.medExApi
                .postDantistExamination(dataNewDantistExamination)
                this.getAllDantistExamination()
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
        
        const formStyle = {
            marginTop: '2vh',
            border: '1px solid #dfdfdf',
            background:'white'
        }

        const formGroup = {
            width: '90%',

        }

        const formInputs = {
            background: 'white',
        }

        const {addClickBtn, filterData} = this.state
        const {role} = this.props

        const deatilsForm = filterData.map((item) =>{
                console.log(item)
                return(
                        <div>
                            <div style={{
                                width: '100%',
                                display: 'flex',
                                justifyContent: 'center',
                                flexDirection: 'column',
                                alignItems: 'center'
                            }}>
                                <table border="1" style={{
                                    width: '100%'
                                }}>
                                    <tr>
                                        <th>Дата обстеження</th>
                                        <th>Стан слизистої</th>
                                        <th>Стан ясен</th>
                                        <th>Зубний покрив</th>
                                        <th>Діагноз</th>
                                    </tr>
                                    <tr>
                                        <td>{this.medExApi
                                            .pasreDate(item.dantistExaminationDate)}</td>
                                        <td>{item.mucosaState}</td>
                                        <td>{item.gumsState}</td>
                                        <td>{item.toothSediment}</td>
                                        <td>{item.diagnosis}</td>
                                    </tr>
                                </table>
                            </div>
                        <Button outline primary className="mt10xp"
                            onClick={this.handlerBack}>Назад</Button>
                    </div>
                )
        })

        const formAddAnalisis = () => {
            const {dantistExaminationDate, mucosaState, gumsState, toothSediment, diagnosis} = this.state
            if(addClickBtn){
                return(
                    <Form style={formStyle} mb="3" className="shadowBox">
                            <Row justifyContent="md-center display-2">
                                    <p className="tiitleRegistration text-uppercase text-dark font-weight-light">
                                        
                                    </p>
                            </Row>
                            <Row justifyContent="md-center">
                                <Form.Group style={formGroup} mb="3">
                                    <span className="dateTitleForm" display="flex" >Дата обстеження:</span>
                                    <Form.Input className="text-black"
                                        style={formInputs} lg mt="2" type="date" 
                                        onChange = {this.handlerChange}
                                        id="dantistExaminationDate"
                                        onChange = {this.handlerChange}
                                        value={dantistExaminationDate}
                                        disabled/>
                                    
                                    <span className="dateTitleForm" display="flex" >Стан слизистої:</span>
                                    <Form.Input className="text-black"
                                        style={formInputs} 
                                        lg mt="2" type="text" 
                                        id="mucosaState"
                                        onChange = {this.handlerChange}
                                        value={mucosaState}
                                        disabled/>
                                    
                                    <span className="dateTitleForm" display="flex" >Стан ясен:</span>
                                    <Form.Input rows="5" display="flex" id="gumsState" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {gumsState}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Зубний покрив:</span>   
                                    <Form.Input rows="5" display="flex" id="toothSediment" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {toothSediment}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Діагноз:</span>   
                                    <Form.Input rows="5" display="flex" id="diagnosis" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {diagnosis}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                </Form.Group>  
                            </Row>
                    </Form>
                )
            }
        }

        const userForm = () => {
            switch (role) {
                case 'user':
                    return(
                        <div>
                            {   
                                this.state.childVisible 
                                ? deatilsForm
                                : <DeatailsList typeList='dantistExamination' data={this.state.data}
                                onClick={this.handlerShowDetails}/>
                            }
                        </div>
                    )
                    break

                case 'nurse':
                    return(
                        <div>
                            {   this.state.childVisible 
                                ? deatilsForm
                                : <DeatailsList typeList='dantistExamination' data={this.state.data}
                                onClick={this.handlerShowDetails}/>
                            }
                        </div>
                    )
                    break
            
                case 'doctor':
                        return(
                            <div>
                                {formAddAnalisis()}
                                <Button outline primary className="btn_add" onClick={this.handlerClickAdd}>Додати обстеження</Button>
                                    {   this.state.childVisible 
                                        ? deatilsForm
                                        : <DeatailsList typeList='dantistExamination' data={this.state.data}
                                        onClick={this.handlerShowDetails}/>
                                    }
                            </div>
                        )
                    break

                case 'mainDoctor':
                        return(
                            <div>
                               {   this.state.childVisible 
                                    ? deatilsForm
                                    : <DeatailsList typeList='dantistExamination' data={this.state.data}
                                    onClick={this.handlerShowDetails}/>
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


export default DantistExamination