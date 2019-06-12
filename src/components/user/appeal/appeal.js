import React from 'react'
import { Row, Col, Form , 
    Button, Container
 } from 'bootstrap-4-react'

import MedExApi from '../../medExApi'

import '../user.css'
import DeatailsList from '../detailsList'
export default class Appeal extends React.Component {
    medExApi = new MedExApi()
    constructor(props){
        super(props)
        this.handlerClickAdd = this.handlerClickAdd.bind(this)
        this.handlerChange = this.handlerChange.bind(this)
        this.getAllAppeal = this.getAllAppeal.bind(this)
        this.state = {
            data: [],
            addClickBtn: false,
            dateAppeal: '',
            textAppeal: ''
        }
    }

    componentDidMount(){
        this.getAllAppeal()
    }

    async getAllAppeal(){
        let dataAllAppeal = await this.medExApi
            .getAllAppealId(this.props.idUser)

        this.setState({
            data: [...dataAllAppeal]
        })

        console.log("Appeal " + this.state.data)

    }
   
    async handlerClickAdd(){
        const {addClickBtn,data,dateAppeal, textAppeal} = this.state
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
        console.log(this.props.doctorEmail)
        if(addClickBtn && dateAppeal.length > 1 && textAppeal.length > 1){
            let dataAppeal = {
                appealDate: dateAppeal,
                appealEssence: textAppeal,
                firstAppeal: true,
                patient: personData
            }
            
            let dataNewAppeal = JSON.stringify(dataAppeal)
            console.log(dataNewAppeal)
            await this.medExApi
                .postAppeal(dataNewAppeal, this.props.doctorEmail)
            this.getAllAppeal()
        }   

    }

    handlerChange(e){
        const {id} = e.currentTarget
        this.setState({
            [id]: e.currentTarget.value
        })
    }
    
    render() {
        const {addClickBtn, date, appealText} = this.state
        const {role} = this.props

        const formStyle = {
            marginTop: '2vh',
            border: '1px solid #dfdfdf',
            background:'white'
        }

        const formGroup = {
            width: '40%'
        }

        const formInputs = {
            background: 'white',
        }

        const formAdd = () => {
            if(addClickBtn){
                return (
                    <Form style={formStyle} className="shadowBox">
                        <Form.Group style={formGroup} mb="3">
                            <span className="dateTitleForm" display="flex" >Дата звернення:</span>
                            <Form.Input id="dateAppeal" className="text-black field" style={formInputs}
                                        lg mt="2"
                                        required 
                                        value = {date}
                                        onChange = {this.handlerChange} 
                                        type="date" 
                                        placeholder="Дата"/>
                            
                            <span className="dateTitleForm" display="flex" >Деталі звернення:</span>
                            <Form.Input id="textAppeal" className="text-black field" style={formInputs}
                                        lg mt="2"
                                        required 
                                        value = {appealText}
                                        onChange = {this.handlerChange} 
                                        type="text" 
                                        placeholder="Деталі звернення"/>
                                
                        </Form.Group>  
                </Form>
            )
            }
        }

        const userForm = () => {
                switch (role) {
                    case 'user':
                        return(
                            <DeatailsList typeList='appeal' data={this.state.data}/>
                        )
                        break

                    case 'nurse':
                        return(
                            <div>
                                <DeatailsList typeList='appeal' data={this.state.data}/>
                            </div>
                        )
                        break
                
                    case 'doctor':
                            return(
                                <div>
                                    {formAdd()}
                                    <Button outline primary className="btn_add" onClick={this.handlerClickAdd}>Додати звернення</Button>
                                    <DeatailsList typeList='appeal' data={this.state.data}/>
                                </div>
                            )
                        break

                    case 'mainDoctor':
                            return(
                                <div>
                                   <DeatailsList typeList='appeal' data={this.state.data}/>
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