import React from 'react'
import { Row, Col, Form , 
    Button, InputGroup, Container
 } from 'bootstrap-4-react'
// import LogoSmall from '../img/logoSmall.png'
import {Link} from 'react-router-dom'

import MedExApi from './medExApi'

export default class StoreAppeal extends React.Component{
    medExApi = new MedExApi()
    constructor(props){
        super(props)
        this.state ={
            dataAppeal: []
        }
    }

    async componentDidMount(){
        let dataAppeals = await this.medExApi
            .getDoctorPatientsAppeals(this.props.email)

            this.setState({
                dataAppeal: [...dataAppeals]
            })
            console.log("StoreAppel " + this.state.dataAppeal)
    }

    render(){
        const infoStyle = {
            marginTop: '2vh',
            border: '1px solid #dfdfdf',
            background:'white'
        }
        const appealStyle = {
            color: '#3baeab'
        }
        const showList = this.state.dataAppeal.map( (appeal) => {
            return( <li className="main_block_item" key={appeal.patient.id} onClick={
                (e) => {
                    e.preventDefault()
                    // upDateId(patient.email)
                }
            }>
                    <p style={appealStyle}>{`Дата: ${this.medExApi.pasreDate(appeal.appealDate)}`}</p>
                    <p style={appealStyle}>{`Деталі звернення: ${appeal.appealEssence}`}</p>
                    <p style={appealStyle}>{`Пацієнт: ${appeal.patient.lastName} ${appeal.patient.firstName}`}</p>
                </li>
            )
        })
        return(
            <Container>
                        <Row justifyContent="md-center">
                            <Col style={infoStyle} col="col-md-9 col-sm-10" >
                                <div className="title_block">
                                    <h1 className="title">Список звернень</h1>
                                </div>

                                <div style={{
                                    minHeight: '1000px'
                                }}>
                                    <ul>
                                        {showList}
                                    </ul>
                                </div>
                            </Col>
                        </Row>
                </Container>
        )
    }
}