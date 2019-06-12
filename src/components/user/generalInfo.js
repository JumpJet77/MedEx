import React from 'react'
import { Row, Col, Form , 
    Button, InputGroup, Container
 } from 'bootstrap-4-react'
 import MedExApi from '../medExApi'
 
 import Loading from '../../img/loading.gif'
 
export default class GeneralInfo extends React.Component{
    medExApi = new MedExApi()
    render() {

        const formStyle = {
            marginTop: '1px',
            border: '1px solid #dfdfdf',
            background:'white',
            width: '100%'
          }

        const formGroup = {
            width: '85%'
        }

        const general_content ={
            width: '100%'
        }

        const { isDoctor } = this.props

        const changeField =  () => {
            // if( isDoctor ){
                return(
                    <Button 
                        outline
                        onClick={this.handlerClickChange} 
                        primary>Змінити</Button>
                )
            // }

        }

        const showGeneralInfo = this.props.data.map((item) => {
            
                return(
                    <Form.Group style={formGroup} >

                        <p className="tiitleRegistration text-uppercase text-dark font-weight-light">
                            Особисті данні
                        </p>

                        <InputGroup mb="2">
                            <InputGroup.Prepend>
                                <InputGroup.Text>ПІБ</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Input value={`${item.person.lastName} ${item.person.firstName} ${item.person.middleName}`}/>
                            <InputGroup.Prepend>
                                { changeField }
                            </InputGroup.Prepend>
                        </InputGroup>

                        <InputGroup mb="2">
                            <InputGroup.Prepend>
                                <InputGroup.Text>Військове звання</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Input value={item.person.range} />
                            {/* <InputGroup.Prepend>
                                <Button outline primary>Змінити</Button>
                            </InputGroup.Prepend> */}
                        </InputGroup>

                        <InputGroup mb="2">
                            <InputGroup.Prepend>
                                <InputGroup.Text>Місце служби(підрозділ)</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Input value={item.person.servicePlace} />
                            {/* <InputGroup.Prepend>
                                <Button outline primary>Змінити</Button>
                            </InputGroup.Prepend> */}
                        </InputGroup>   

                        <InputGroup mb="2">
                            <InputGroup.Prepend>
                                <InputGroup.Text>Дата народження</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Input value={
                                this.medExApi.pasreDate(item.person.birthDate)
                                } />
                            {/* <InputGroup.Prepend>
                                <Button style={btnChange} outline primary>Змінити</Button>
                            </InputGroup.Prepend> */}
                        </InputGroup>  

                        <InputGroup mb="2">
                            <InputGroup.Prepend>
                                <InputGroup.Text>Національність</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Input value={item.nationality}/>
                            {/* <InputGroup.Prepend>
                                <Button outline primary>Змінити</Button>
                            </InputGroup.Prepend> */}
                        </InputGroup>  

                        <InputGroup mb="2">
                            <InputGroup.Prepend>
                                <InputGroup.Text>Освіта</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Input value={item.education} />
                            {/* <InputGroup.Prepend>
                                <Button outline primary>Змінити</Button>
                            </InputGroup.Prepend> */}
                        </InputGroup> 

                        <InputGroup mb="2">
                            <InputGroup.Prepend>
                                <InputGroup.Text>Рік призову(початок служби)</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Input value={
                                this.medExApi.pasreDate(item.person.birthDate)
                                } />
                            {/* <InputGroup.Prepend>
                                <Button outline primary>Змінити</Button>
                            </InputGroup.Prepend> */}
                        </InputGroup>   

                        <InputGroup mb="2">
                            <InputGroup.Prepend>
                                <InputGroup.Text>Сімейний стан</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Input value={item.familyState} />
                            {/* <InputGroup.Prepend>
                                <Button outline primary>Змінити</Button>
                            </InputGroup.Prepend> */}
                        </InputGroup>

                        <p className="tiitleRegistration text-uppercase text-dark font-weight-light">
                            Контактна інформація
                        </p>

                        <InputGroup mb="2">
                            <InputGroup.Prepend>
                                <InputGroup.Text>Домашня адреса</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Input value={item.person.homeAddress} />
                            {/* <InputGroup.Prepend>
                                <Button outline primary>Змінити</Button>
                            </InputGroup.Prepend> */}
                        </InputGroup>

                        <InputGroup mb="2">
                            <InputGroup.Prepend>
                                <InputGroup.Text>Email</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Input value={item.person.email} />
                            {/* <InputGroup.Prepend>
                                <Button outline primary>Змінити</Button>
                            </InputGroup.Prepend> */}
                        </InputGroup>

                        <InputGroup mb="2">
                            <InputGroup.Prepend>
                                <InputGroup.Text>Телефон</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Input value={item.person.phoneNumber} />
                            {/* <InputGroup.Prepend>
                                <Button outline primary>Змінити</Button>
                            </InputGroup.Prepend> */}
                        </InputGroup>

                    </Form.Group>)
        })

        return (
                <Container>
                        <Row justifyContent="">
                            <div style={general_content}>
                                <Form style={formStyle}>
                                <Row mt="3" justifyContent="md-center display-2">
                                            <h3 className="tiitleRegistration text-uppercase text-dark font-weight-light">
                                                Профіль військовослужбовця
                                            </h3>
                                    </Row>
                                    <Row justifyContent="md-center" style={{height : "100vh"}}>
                                        {this.props.data.length==0
                                            ?<div><img src={Loading}/></div>
                                            :showGeneralInfo}
                                    </Row>
                                </Form>
                            </div>
                        </Row>
                </Container>
            )
        
    }
}