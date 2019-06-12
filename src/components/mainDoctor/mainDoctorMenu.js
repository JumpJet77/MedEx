import React from 'react'
import { Navbar, Nav, Button, Dropdown, Form, Collapse } from 'bootstrap-4-react'
// import LogoSmall from '../img/logoSmall.png'
import {Link} from 'react-router-dom'

export default class MainDoctorMenu extends React.Component{
    constructor(props){
        super(props)
        this.handlerClick = this.handlerClick.bind(this)
        this.onChangeSearch = this.onChangeSearch.bind(this)
        this.state = {
            search: ''
        }
    }

    handlerClick(e){
        e.preventDefault()
        this.props.search(this.state.search)
    }

    onChangeSearch(e){
        e.preventDefault()
        const {id, value} = e.currentTarget

        this.setState({
            [id]: value
        })
    }
    
    render(){
        const {role, logout} = this.props
        
        const doctorMenu = () => {
            return(
                <Navbar.Nav mr="auto">

                    <Nav.Item >
                        <Nav.Link>
                            <Link to = "/statistics"> СТАТИСТИКА </Link>
                        </Nav.Link>
                    </Nav.Item>
                    
                </Navbar.Nav>
            )
        }

        const menu = () => {
            switch (role) {
                    
                case 'nurse':
                    return nurseMenu()
                    break;

                case 'mainDoctor':
                    return doctorMenu()
                    break;
            
                default:
                    break;
            }
        }

        const nurseMenu = () => {
            return(
                <Navbar.Nav mr="auto">

                    <Nav.Item >
                        <Nav.Link>
                            <Link to = "/adduser"> ЗАРЕЄСТРУВАТИ ПАЦІЄНТА </Link>
                        </Nav.Link>
                    </Nav.Item>
                </Navbar.Nav>
            )
        }

        return(
            <Navbar expand="lg" light bg="light">
               
                <Navbar.Toggler target="#navbarSupportedContent" />
                <Collapse navbar id="navbarSupportedContent">

                    <Nav.Item active>
                        <Nav.Link > <Link to="/">ГОЛОВНА</Link> </Nav.Link>
                    </Nav.Item>
                    
                    {
                        menu()
                    }
                    
                
                    <Form inline ml="3" my="2 lg-0">
                        <Link to = "/" onClick={this.props.logout}><i class="fa fa-sign-out" aria-hidden="true"></i></Link>
                    </Form>
                </Collapse>
            </Navbar>
        )
    }
}