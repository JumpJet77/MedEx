import React from 'react'
import { Navbar, Nav, Button, Dropdown, Form, Collapse } from 'bootstrap-4-react'
// import LogoSmall from '../img/logoSmall.png'
import {Link} from 'react-router-dom'

export default class MainDoctorMenu extends React.Component{

    render(){
        const {role} = this.props
        
        const doctorMenu = () => {
            return(
                <Navbar expand="lg" light bg="light">
                <Navbar.Toggler target="#navbarSupportedContent" />
                <Collapse navbar id="navbarSupportedContent">
                        <Nav.Item active>
                            <Nav.Link > <Link to="/" onClick={this.props.getAllPatient}>ГОЛОВНА</Link> </Nav.Link>
                        </Nav.Item>

                        <Navbar.Nav mr="auto">

                            <Nav.Item >
                                <Nav.Link>
                                    <Link to = "/statistics"> СТАТИСТИКА </Link>
                                </Nav.Link>
                            </Nav.Item>

                            <Nav.Item >
                                <Nav.Link><Link to="/store">ЖУРНАЛ ЗВЕРНЕНЬ</Link></Nav.Link>
                            </Nav.Item>

                        </Navbar.Nav>
                        

                        <Form inline ml="3" my="2 lg-0">
                            <Link to = "/" onClick={this.props.logout}><i class="fa fa-sign-out" aria-hidden="true"></i></Link>
                        </Form>

                    </Collapse>
                </Navbar>
            )
        }

        return(
            <div>
            {
                role == 'mainDoctor' || role == 'nurse'
                    ? null
                    :doctorMenu()
            }
            </div>
        )
    }
}