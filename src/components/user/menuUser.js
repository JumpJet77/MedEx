import React from 'react'
import { Navbar, Nav, Button, Dropdown, Form, Collapse } from 'bootstrap-4-react'
// import LogoSmall from '../img/logoSmall.png'
import {Link} from 'react-router-dom'

export default class MenuUser extends React.Component{
    render(){
        const userMenu = () => {
            if(this.props.role == "user"){
                return(
                    <Nav.Item active>
                        <Nav.Link > <Link to="/">ГОЛОВНА</Link> </Nav.Link>
                    </Nav.Item>
                )
            }else{
                return(
                    <Nav.Item active>
                        <Nav.Link > <Link to="/users/user">ГОЛОВНА ІНФОРМАЦІЯ</Link> </Nav.Link>
                    </Nav.Item>
                )
            }
        }

        const logOutForm = () => {
            if(this.props.role == "user"){
                return (
                    <Link to = "/" onClick={this.props.logout}><i class="fa fa-sign-out" aria-hidden="true"></i></Link>
                )
            }
        } 

        return(
            <Navbar expand="lg" light bg="light">
                <Navbar.Brand href="#">
                    {/* <img src={LogoSmall} alt=""/>  */}
                </Navbar.Brand>
                <Navbar.Toggler target="#navbarSupportedContent" />
                <Collapse navbar id="navbarSupportedContent">
                <Navbar.Nav mr="auto">
                    
                    {userMenu()}
                   
                    <Nav.Item dropdown>
                        <Nav.Link dropdownToggle><Link to="">ІСТОРІЯ ЗВЕРНЕНЬ ТА ХВОРОБ</Link> </Nav.Link>
                        <Dropdown.Menu>
                            <Dropdown.Item><Link to="/users/list/user/appeal">ЗВЕРНЕННЯ</Link></Dropdown.Item>
                            {/* <Dropdown.Item><Link to="/users/list/user/disease">ХВОРОБИ</Link></Dropdown.Item> */}
                            <Dropdown.Item><Link to="/users/list/user/treatment">ІСТОРІЯ ХВОРОБ</Link></Dropdown.Item>
                        </Dropdown.Menu>
                    </Nav.Item>

                    <Nav.Item dropdown>
                        <Nav.Link dropdownToggle><Link to="">ОБСТЕЖЕННЯ</Link></Nav.Link>
                        <Dropdown.Menu>
                            <Dropdown.Item><Link to="/user/examination">ОБСТЕЖЕННЯ</Link></Dropdown.Item>
                            <Dropdown.Item><Link to="/user/physicalexamination">ФІЗИЧНИЙ РОЗВИТОК</Link></Dropdown.Item>
                            <Dropdown.Item><Link to="/user/dantist">СТОМАТОЛОГ</Link></Dropdown.Item>
                        </Dropdown.Menu>
                    </Nav.Item>

                    <Nav.Item >
                        <Nav.Link>
                            <Link to = "/analisis"> АНАЛІЗИ </Link>
                        </Nav.Link>
                    </Nav.Item>

                    <Nav.Item >
                        <Nav.Link><Link to="/vaccination">ВАКЦИНАЦІЯ</Link></Nav.Link>
                    </Nav.Item>

                </Navbar.Nav>
                {logOutForm()}
                </Collapse>
            </Navbar>
        )
    }
}