import React from 'react'
import { Container, Row, Col, Form , 
    Button
 } from 'bootstrap-4-react'

 import MedExApi from '../../medExApi'

import DeatailsList from '../detailsList'

class PhysicalExamination extends React.Component {
    medExApi = new MedExApi()
    constructor(props){
        super(props)
        this.handlerClickAdd = this.handlerClickAdd.bind(this)
        this.handlerChange =  this.handlerChange.bind(this)
        this.getAllPhysicalExamination = this.getAllPhysicalExamination.bind(this)
        this.handlerBack = this.handlerBack.bind(this)
        this.handlerShowDetails = this.handlerShowDetails.bind(this)
        this.state = {
            data: [],
            addClickBtn: false,
            physicalExaminationDate: '',
            height: '',
            weight: '',
            circumferenceCalm: '',
            circumferenceBreatheIn: '',
            circumferenceBreatheOut: '',
            spirometry: '',
            dynamometryLeftHand: '',
            dynamometryRightHand: '',
            circumferenceStomach: '',
            filterData: [],
            childVisible: false
        }
    }

    componentDidMount(){
        this.getAllPhysicalExamination()
        console.log(this.state.data)
    }

    async getAllPhysicalExamination(){
        let dataAllPhysicalExamination = await this.medExApi
            .getAllPhysicalExamination(this.props.idUser)

        this.setState({
            data: [...dataAllPhysicalExamination]
        })

    }

    async handlerClickAdd(){
        let personData
        const {circumferenceBreatheIn, circumferenceCalm, circumferenceBreatheOut, circumferenceStomach,
         dynamometryLeftHand, dynamometryRightHand, height, spirometry, physicalExaminationDate,
            weight, addClickBtn, } = this.state
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

        if(addClickBtn && physicalExaminationDate.length > 1 ){
            let dataPhysicalExamination = {
                physicalExaminationDate,
                height,
                weight,
                circumferenceCalm,
                circumferenceBreatheIn,
                circumferenceBreatheOut,
                spirometry,
                dynamometryLeftHand,
                dynamometryRightHand,
                circumferenceStomach,
                patient: personData
            }
            
            let dataNewPhysicalExamination = JSON.stringify(dataPhysicalExamination)
            console.log("" + dataNewPhysicalExamination)
            await this.medExApi
                .postPhysicalExamination(dataNewPhysicalExamination)
                this.getAllPhysicalExamination()
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
                                        <th>Зріст</th>
                                        <th>Вага</th>
                                        <th>Окружність грудної клітини (Спокійно)</th>
                                        <th>Окружність грудної клітини (Вдих)</th>
                                    </tr>
                                    <tr>
                                        <td>{this.medExApi
                                            .pasreDate(item.physicalExaminationDate)}</td>
                                        <td>{item.height}</td>
                                        <td>{item.weight}</td>
                                        <td>{item.circumferenceCalm}</td>
                                        <td>{item.circumferenceBreatheIn}</td>
                                    </tr>
                                </table>

                                <table border="1" style={{
                                    width: '100%',
                                    marginTop: '15px'
                                }}>
                                    <tr>
                                        <th>Окружність грудної клітини (Видих)</th>
                                        <th>Спірометрія</th>
                                        <th>Динамометрія ліва рука</th>
                                        <th>Динамометрія права рука</th>
                                        <th>Окружність живота</th>
                                    </tr>
                                    <tr>
                                        <td>{item.circumferenceBreatheOut}</td>
                                        <td>{item.spirometry}</td>
                                        <td>{item.dynamometryLeftHand}</td>
                                        <td>{item.dynamometryRightHand}</td>
                                        <td>{item.circumferenceStomach}</td>
                                    </tr>
                                </table>
                            </div>
                        <Button outline primary className="mt10xp"
                            onClick={this.handlerBack}>Назад</Button>
                    </div>
                )
        })

        const formAddAnalisis = () => {
            const {circumferenceBreatheIn, circumferenceCalm, circumferenceBreatheOut, circumferenceStomach,
                dynamometryLeftHand, dynamometryRightHand, height, spirometry, physicalExaminationDate,
                   weight, addClickBtn} = this.state
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
                                        id="physicalExaminationDate"
                                        onChange = {this.handlerChange}
                                        value={physicalExaminationDate}
                                        disabled/>
                                    
                                    <span className="dateTitleForm" display="flex" >Зріст:</span>
                                    <Form.Input className="text-black"
                                        style={formInputs} 
                                        lg mt="2" type="text" 
                                        id="height"
                                        onChange = {this.handlerChange}
                                        value={height}
                                        disabled/>
                                    
                                    <span className="dateTitleForm" display="flex" >Вага:</span>
                                    <Form.Input rows="3" display="flex" id="weight" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {weight}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Окружність грудної клітини (Спокійно):</span>   
                                    <Form.Input rows="3" display="flex" id="circumferenceCalm" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {circumferenceCalm}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Окружність грудної клітини (Вдих):</span>   
                                    <Form.Input rows="3" display="flex" id="circumferenceBreatheIn" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {circumferenceBreatheIn}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Окружність грудної клітини (Видих):</span>   
                                    <Form.Input rows="3" display="flex" id="circumferenceBreatheOut" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {circumferenceBreatheOut}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Спірометрія:</span>   
                                    <Form.Input rows="3" display="flex" id="spirometry" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {spirometry}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Динамометрія ліва рука:</span>   
                                    <Form.Input rows="3" display="flex" id="dynamometryLeftHand" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {dynamometryLeftHand}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Динамометрія права рука:</span>   
                                    <Form.Input rows="3" display="flex" id="dynamometryRightHand" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {dynamometryRightHand}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>


                                    <span className="dateTitleForm" display="flex" >Окружність живота:</span>   
                                    <Form.Input rows="3" display="flex" id="circumferenceStomach" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {circumferenceStomach}
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
                                : <DeatailsList typeList='physicalExamination' data={this.state.data}
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
                                : <DeatailsList typeList='physicalExamination' data={this.state.data}
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
                                        : <DeatailsList typeList='physicalExamination' data={this.state.data}
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
                                    : <DeatailsList typeList='physicalExamination' data={this.state.data}
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


export default PhysicalExamination