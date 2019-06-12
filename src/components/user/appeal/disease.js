import React from 'react';
import { Row, Col, Form , 
    Button, Container
 } from 'bootstrap-4-react';

import '../user.css'
import DeatailsList from '../detailsList'
export default class Disease extends React.Component {

    constructor(props){
        super(props)
        this.handlerClickAdd = this.handlerClickAdd.bind(this)
        this.handlerChange = this.handlerChange.bind(this)
        this.state = {
            data:[],
            addClickBtn: false,
            dateDisease: '',
            textDisease: ''
        }
    }

    componentDidMount(){
        let data =[
            
        ]

        this.setState({
            data: data
        })
    }

   
    handlerClickAdd(){
        const {addClickBtn,dateDisease, textDisease} = this.state

        this.setState({
            addClickBtn: !this.state.addClickBtn
        })
        
        if(addClickBtn && dateDisease.length > 1 && textDisease.length > 1){
            let dataDisease = {
                date: dateDisease,
                text: textDisease,
            }

            let data = JSON.stringify(dataDisease)

            this.setState({
                textAppeal: '',
                textDisease: ''
            })
        }
    }

    handlerChange(e){
        const {id} = e.currentTarget
        this.setState({
            [id]: e.currentTarget.value
        })
    }
    
    render() {
        const {addClickBtn, dateDisease, textDisease} = this.state
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
                            <span className="dateTitleForm" display="flex" >Дата захворювання:</span>
                            <Form.Input id="dateDisease" className="text-black field" style={formInputs}
                                        lg mt="2"
                                        required 
                                        value = {dateDisease}
                                        onChange = {this.handlerChange} 
                                        type="date" 
                                        placeholder="Дата"/>
                            
                            <span className="dateTitleForm" display="flex" >Діагноз:</span>
                            <Form.Input id="textDisease" className="text-black field" style={formInputs}
                                        lg mt="2"
                                        required 
                                        value = {textDisease}
                                        onChange = {this.handlerChange} 
                                        type="text" 
                                        placeholder="Діагноз"/>
                                
                        </Form.Group>  
                </Form>
            )
            }
        }

        const userForm = () => {
            switch (role) {
                case 'user':
                    return(
                        <DeatailsList data={this.state.data}/>
                    )
                    break

                case 'nurse':
                    return(
                        <div>
                            {
                                <DeatailsList data={this.state.data} onClick={this.handlerShowDetails}/>
                            }
                        </div>
                    )
                    break
            
                case 'doctor':
                        return(
                            <div>
                                {formAdd()}
                                <Button outline primary className="btn_add" 
                                    onClick={this.handlerClickAdd}>Додати хворобу</Button>
                                <DeatailsList data={this.state.data}/>
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