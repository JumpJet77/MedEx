import React from 'react'
import { Container, Row, Col, Form , 
    Button,
 } from 'bootstrap-4-react'


import LogoBig from '../../img/logo.png'
// import UserCard from '../user/userPanel'
// import TestPage from '../testPage'
import DoctorPanel from '../doctor/doctorPanel'
import MainDoctorPanel from '../mainDoctor/mainDoctorPanel'
import UserCard from '../user/userPanel'
import MedExApi from '../medExApi'
import jwtDecode from 'jwt-decode'
import {Route} from 'react-router-dom'
import {Link} from 'react-router-dom'

export default class LoginForm extends React.Component {
    medExApi = new MedExApi()
    constructor(props){
        super(props)
    
        this.handlerAgree = this.handlerAgree.bind(this)
        this.handlerChange = this.handlerChange.bind(this)
        this.validateEmail = this.validateEmail.bind(this)
        this.handlerClick = this.handlerClick.bind(this)
        this.validateForm = this.validateForm.bind(this)
        this.parseJwt = this.parseJwt.bind(this)
        this.logout = this.logout.bind(this)
        this.state = {
            login: '',
            password: '',
            agree: false,
            emailValid: false,
            isLogin: false,
            role: '',
            token: '',
            path: '/'
        }
    }

    validateEmail(){
        let{login} = this.state

        let email = this.medExApi
            .validEmail(login)

        this.setState({
            emailValid: email
        })

    }

    validateForm(){
        const {password, agree, isLogin, role} = this.state
        
        if(agree && role.length > 1 && password.length > 4){
            this.setState({
                isLogin: !isLogin
            })
        }else{
            this.setState({
                isLogin: false
            })
        }

    }

    handlerAgree(e){
        this.setState({
            agree: e.currentTarget.checked
        })
    }

    handlerChange(e){
        const {id} = e.currentTarget
        this.setState({
            [id]: e.currentTarget.value
        })
        this.validateEmail()
    }

    async logout(){
        let logOutStatus = await this.medExApi
                    .logOut(this.state.token)
        if(logOutStatus){
            this.setState({
                login: '',
                password: '',
                agree: false,
                emailValid: false,
                isLogin: false,
                role: '',
                token: '',
                path: '/'
            })
        }
    }

    parseJwt(token){
        let decoded = jwtDecode(token)
        let str = decoded.role[0]
        let arr = str.split(",")
        let mainRole = ''
        let newPath = ''
        console.log(arr[0])
          switch (arr[0]) {

                case 'ROLE_DOCTOR':
                    mainRole = 'doctor'
                    break;
                
                case 'ROLE_NURSE':
                    mainRole = 'nurse'
                  break;

                case 'ROLE_USER':
                    mainRole = 'user'
                    break;

                case 'ROLE_ADMIN':
                    mainRole = 'mainDoctor'
                    break;
          
              default:
                  break;
          }  
        this.setState({
            role: mainRole,
            path: newPath,
            isLogin: true
        })
        
    }

    async handlerClick(e){
        const {login, password, emailValid} = this.state
        e.preventDefault()
        
        if(emailValid){
            let data = JSON.stringify( {
                username: login,
                password: password
            })
            let token = await this.medExApi
                .autorithation(data)
            
            this.setState({
                token: token
            })
            
        }
        
        this.parseJwt(this.state.token)
        
    }

    render() {
        const formStyle = {
            marginTop: '7vh',
            border: '1px solid #dfdfdf',
            background:'white',
            marginButtom:'50%'
          };

        const formGroup = {
            width: '80%',
        };

        const formInputs = {
            background: 'white',
        };

        const {login, password, isLogin, role} = this.state
       
        const renderPanel = () => {
            switch (role) {
                case 'user':
                    return <Route path="/" component={() =>{
                        return <UserCard role="user" email={login} logout={this.logout}/>
                    }} exact/>

                case 'doctor':
                    return (
                        <Route path="/" component={() =>{
                            return <DoctorPanel role="doctor" email={login} logout={this.logout}/>
                        }} exact/>
                    ) 
                
                case 'nurse':
                    return <MainDoctorPanel role="nurse" logout={this.logout} doctorEmail={this.state.login}/>

                case 'mainDoctor':
                    return (
                        <Route path="/" component={() =>{
                            return <MainDoctorPanel role="mainDoctor"
                            logout={this.logout} 
                            doctorEmail={this.state.login}/>
                        }} exact/>)
                    
                default:
                    break;
            }
        }

        if(isLogin){    
            return(
                <div>
                    {
                        renderPanel()
                    }
                </div>
            )
        }

       return (
           <Container style={{background: '#3baeab', height: '100vh'}}>
                <Row justifyContent="md-center">
                    <Col  col="col-md-6 col-sm-8" justifyContent="md-center">
                        <Form style={formStyle} mb="4" className="shadowBox">
                            <Row mt="4" justifyContent="md-center display-2">
                                        <img src={LogoBig} alt=""/>
                            </Row>
                            <Row justifyContent="md-center">
                                <Form.Group style={formGroup} mb="4">
                                    <Form.Input id="login" className="text-black field" style={formInputs}
                                        lg mt="2"
                                        required 
                                        value = {login}
                                        onChange = {this.handlerChange} 
                                        type="email" 
                                        placeholder="Login"/>
                                    
                                    <Form.Input className="text-black field" id="password" style={formInputs} lg mt="2" 
                                        value = {password} 
                                        required
                                        onChange = {this.handlerChange}
                                        type="password" 
                                        placeholder="Password"/>
                                    <label>
                                    
                                    <p className="loginWarningTitle">Працюєте на чужому комп'ютері? 
                                        Використовуйте режим "Приватний перегляд".</p>

                                        <input type="checkbox" onChange={this.handlerAgree}/> З правилами згоден
                                    </label>

                                    <Row justifyContent="md-around">
                                        
                                    <Link to={this.state.path}>
                                        <Button className="btn btn-lg btn-primary" 
                                            primary mt="3" 
                                            onClick={this.handlerClick}  
                                            type="button">Увійти в систему</Button>
                                    </Link>
                                        
                                    </Row>
                                </Form.Group>
                            </Row>
                        </Form>
                    </Col>
                </Row>
            </Container>
        )
    }
}

