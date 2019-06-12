import React from 'react'
import { Row, Col, Form , 
    Button, Container
 } from 'bootstrap-4-react'

import MedExApi from './medExApi'
import swal from 'sweetalert'
import axios from 'axios'
import '../index.css'

export default class Registration extends React.Component {
    medExApi = new MedExApi()
    constructor(props){
        super(props)

        this.state = {
            firstName: '',
            name: '',
            surname: '',
            rang: '',
            placeOfCob: '',
            birthday: '',
            nationality: '',
            facultyId: '',
            education: '',
            dateOfCob: '',
            marialStatus: '',
            homeAddress: '',
            email: '',
            validEmail: false,
            numberPhone: '',
            validNumberPhone: false,
            password : '',
            passwordAgain : '',
            validPass: false,
            heredityDiseaseName: '',
            specialNotices: '',
            faculData: []
        }

        this.handlerChange = this.handlerChange.bind(this)
        this.handlerClick = this.handlerClick.bind(this)
        this.handlerClickOption = this.handlerClickOption.bind(this)
    }

    async componentDidMount(){
        let data = await this.medExApi.getAllFaculty()

        this.setState({
            faculData: [...data.faculties]
        })

    }

    handlerClickOption(e){
        const {id} = e.currentTarget
        this.setState({
            facultyId: id
        })
    }

    handlerChange(e){
        const {email} = this.state
        const {id, value} = e.currentTarget
        let checkEmail = this.medExApi
                             .validEmail(email)
        this.setState({
            [id]: value,
            validEmail : checkEmail
        })
    }

    handlerClick(e){
        e.preventDefault()
        const {validEmail} = this.state
        let registrationStatus = false
        const inputsValue = {
            person : {
                email: this.state.email,
                password : this.state.password,
                lastName: this.state.firstName,
                firstName: this.state.name,
                middleName: this.state.surname,
                birthDate: this.state.birthday,
                roles: [
                    {
                        "idRole": 5,
                        "role": "ROLE_USER"
                    }
                ],
                range: this.state.rang,
                servicePlace: this.state.placeOfCob,
                homeAddress: this.state.homeAddress,
                phoneNumber: this.state.numberPhone,
            },
            nationality: this.state.nationality,
            education: this.state.education,
            recruitmentDate: this.state.dateOfCob,
            familyState: this.state.marialStatus,
            heredityDiseaseName: this.state.heredityDiseaseName,
            specialNotices: this.state.specialNotices
        }
        console.log(this.state.faculData)
        console.log(this.state.facultyId)
        let data = JSON.stringify(inputsValue)

        if(validEmail){
            registrationStatus = this.medExApi
                .addPatient(data, this.state.facultyId)
                if(registrationStatus){
                    this.setState({
                        firstName: '',
                        name: '',
                        surname: '',
                        rang: '',
                        placeOfCob: '',
                        birthday: '',
                        nationality: '',
                        education: '',
                        dateOfCob: '',
                        marialStatus: '',
                        homeAddress: '',
                        facultyName: '',
                        email: '',
                        numberPhone: '',
                        password : '',
                        passwordAgain : '',
                        heredityDiseaseName: '',
                        specialNotices: ''
                    })
                }
        }
    }

    

    
    render() {

        const { firstName, name, surname,
            rang, email, numberPhone, password, passwordAgain, nationality,
        birthday, education, dateOfCob, marialStatus, homeAddress, placeOfCob,
        heredityDiseaseName, specialNotices} = this.state

        const formStyle = {
            marginTop: '2vh',
            border: '1px solid #dfdfdf',
            background:'white'
          }

        const formInputs = {
            background: 'white',
        }


        const showoption = this.state.faculData.map( (facult) => {
            return(
                <option id={`${facult.id}`} onClick={this.handlerClickOption}>{`${facult.facultyName}`}</option>
            )
        })

       return (
        
            <Container>   
                <Row justifyContent="md-center">
                    <Col col="col-md-10 col-sm-10" >
                        <Form style={formStyle} className="shadowBox from">
                            <Row mt="3" justifyContent="md-center display-2">
                                    <p className="tiitleRegistration text-uppercase text-dark font-weight-light">
                                        Форма реєстрації військовослужбовця
                                    </p>
                            </Row>
                            <Row justifyContent="md-center">
                                <Col col="col-md-11 col-sm-12" >
                                <Form.Group  mb="3">
                                    
                                    <Form.Input className="text-black field" style={formInputs} 
                                        // required
                                        lg mt="2" type="text" 
                                        id="firstName"
                                        value = {firstName}
                                        onChange={this.handlerChange}
                                        placeholder="Прізвище"/>
                                        
                                    <Form.Input className="text-black field" style={formInputs} 
                                        // required
                                        lg mt="2" type="text"
                                        id="name"
                                        onChange={this.handlerChange}
                                        value = {name} 
                                        placeholder="Імʼя"/>
                                        
                                    <Form.Input className="text-black field" style={formInputs} 
                                        // required
                                        lg mt="2" type="text"
                                        id="surname" 
                                        onChange={this.handlerChange}
                                        value = {surname}
                                        placeholder="По-батькові"/>
                                    
                                    <Form.Input className="text-black field" style={formInputs} 
                                        // required
                                        lg mt="2" type="text" 
                                        id="rang"
                                        onChange={this.handlerChange}
                                        value = {rang}
                                        placeholder="Військове звання"/>
                                    
                                    <Form.Input className="text-black field" style={formInputs} 
                                        // required
                                        lg mt="2" type="text" 
                                        id="placeOfCob"
                                        onChange={this.handlerChange}
                                        value = {placeOfCob}
                                        placeholder="Місце служби(підрозділ)"/>

                                    <span className="dateTitleForm" display="flex" >Вкажіть дату народження:</span>
                                    <Form.Input className="text-black field" style={formInputs} 
                                        // required
                                        lg type="date"
                                        id="birthday"
                                        onChange={this.handlerChange}
                                        value = {birthday} 
                                        placeholder="Дата народження"/>
                                    
                                    <Form.Input className="text-black field" style={formInputs} 
                                        // required
                                        lg mt="2" type="text" 
                                        id="nationality"
                                        onChange={this.handlerChange}
                                        value = {nationality}
                                        placeholder="Національність"/>
 
                                     <Form.Input className="text-black field" style={formInputs} 
                                        // required
                                        lg mt="2" type="text" 
                                        id="education"
                                        onChange={this.handlerChange}
                                        value = {education}
                                        placeholder="Освіта"/>

                                    <span className="dateTitleForm" display="flex" >Вкажіть дату призову (початку служби):</span>
                                    <Form.Input className="text-black field" style={formInputs} 
                                        // required
                                        lg mt="2" type="date" 
                                        id="dateOfCob"
                                        onChange={this.handlerChange}
                                        value = {dateOfCob}
                                        placeholder="Рік призову(початок служби)"/>
                                    
                                    <Form.Input className="text-black field" style={formInputs} 
                                        lg mt="2" type="text"
                                        // required 
                                        id="marialStatus"
                                        onChange={this.handlerChange}
                                        value = {marialStatus}
                                        placeholder="Сімейний стан"/>
                                    
                                    <Form.Input className="text-black field" style={formInputs} 
                                        lg mt="2" type="text" 
                                        // required
                                        id="homeAddress"
                                        onChange={this.handlerChange}
                                        value = {homeAddress}
                                        placeholder="Домашня адреса"/>

                                    <select style={{
                                        width: '100%',
                                        padding: '15px',
                                        marginTop: '10px',
                                        background: 'white',
                                        color: '#6c757d',
                                        fontSize: '22px'
                                    }}>
                                        {showoption}
                                    </select>
                                    
                                    <Form.Input className="text-black field" style={formInputs} 
                                        lg mt="2" type="email"
                                        // required
                                        id="email"
                                        onChange={this.handlerChange}
                                        value = {email} 
                                        placeholder="Email"/>

                                    <Form.Input className="text-black field" style={formInputs} 
                                        lg mt="2" type="tel" 
                                        // required
                                        id="numberPhone"
                                        onChange={this.handlerChange}
                                        value = {numberPhone}
                                        placeholder="+380"/>

                                    <Form.Input className="text-black field" style={formInputs} 
                                        lg mt="2" type="password" 
                                        // required
                                        id="password"
                                        onChange={this.handlerChange}
                                        value = {password}
                                        placeholder="Пароль"/>
                                    
                                    <p className="registrationWarningTitle">*Пароль повинен містити не менше восьми знаків, включати літери, цифри і спеціальні символи</p>
                                    <Form.Input className="text-black field" style={formInputs} 
                                        lg mt="2" type="password"
                                        // required
                                        id="passwordAgain"
                                        onChange={this.handlerChange}
                                        value = {passwordAgain} 
                                        placeholder="Повторіть пароль"/>

                                    <textarea rows="3" display="flex" id="heredityDiseaseName" 
                                        className="moreInfoRegistration" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {heredityDiseaseName}
                                        onChange = {this.handlerChange} 
                                        placeholder="Попередні захворювання"/>

                                    <textarea rows="3" display="flex" id="specialNotices" 
                                        className="moreInfoRegistration" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {specialNotices}
                                        onChange = {this.handlerChange} 
                                        placeholder="Примітки"/>
                                        
                                    <Button primary mt="3" type="submit" 
                                        onClick={this.handlerClick}
                                    >Додати користувача</Button>

                                </Form.Group>  
                                </Col>
                            </Row>
                        </Form>
                    </Col>
                </Row>
            </Container>
        )
    }
}