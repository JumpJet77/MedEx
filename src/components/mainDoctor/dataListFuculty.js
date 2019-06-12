import React from 'react'
import { Row, Col, Form , 
    Button, InputGroup, Container
 } from 'bootstrap-4-react'
import {Link} from 'react-router-dom'
 
export default class DataListFaculty extends React.Component{
    constructor(props){
        super(props)
        this.state = {
            
        }
    }

    componentDidMount(){
        
    }

    render() {

        const infoStyle = {
            marginTop: '2vh',
            border: '1px solid #dfdfdf',
            background:'white'
        }

        const {upDateId, data, title, path} = this.props

        const showList = data.map( (facult) => {
            return( <li className="main_block_item" key={facult.id} onClick={
                (e) => {
                    e.preventDefault()
                    upDateId(facult.id)
                }
            }>
                    <Link to={`/users`}>
                        {`Факультет ${facult.facultyName}  ( Лікар ${facult.doctor.firstName} ${facult.doctor.lastName} )`}
                    </Link>
                </li>
            )
        })

        
        return (
                <Container>
                        <Row justifyContent="md-center">
                            <Col style={infoStyle} col="col-md-10 col-sm-10" >
                                <div className="title_block">
                                    <h1 className="title">{title}</h1>
                                </div>
                                <div style={{
                                    display: 'flex',
                                    justifyContent: 'flex-end'
                                }}>
                                    <Form inline my="2 lg-0">
                                            <Form.Input type="search"
                                                id="search"
                                                placeholder="Пошук" 
                                                mr="sm-2"
                                                // onChange={this.onChangeSearch}
                                                // value={this.state.searchValue} 
                                                />

                                            <Button outline primary my="2 sm-0" mr="3" type="button"
                                                // onClick={this.handlerClick}
                                                >ПОШУК</Button>
                                    </Form>
                                </div>
                                <div className="main_block">
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