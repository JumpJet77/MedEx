import React from 'react'
import { Row, Col, Form , 
    Button, InputGroup, Container
 } from 'bootstrap-4-react'
import {Link} from 'react-router-dom'
 
export default class DataList extends React.Component{
    constructor(props){
        super(props)
        this.handlerClick = this.handlerClick.bind(this)
        this.onChangeSearch = this.onChangeSearch.bind(this)
        this.state = {
            search: '',
            data: this.props.data,
            searchState: false,
            titleSearch: 'Пошук'
        }
    }

    handlerClick(e){
        e.preventDefault()
        this.setState({
            searchState: !this.state.searchState,
            titleSearch: 'Очистити пошук'
        })
        this.props.search(this.state.search, this.state.searchState)
    }

    onChangeSearch(e){
        e.preventDefault()
        const {id, value} = e.currentTarget

        this.setState({
            [id]: value
        })
    }

    render() {

        const infoStyle = {
            marginTop: '2vh',
            border: '1px solid #dfdfdf',
            background:'white'
        }

        const {upDateId, title, path} = this.props

        const showList = this.state.data.map( (patient) => {
            return( <li className="main_block_item" key={patient.id} onClick={
                (e) => {
                    e.preventDefault()
                    upDateId(patient.email)
                }
            }>
                    <Link to={`/users/user`}>
                        {`${patient.lastName} ${patient.firstName} ( ${patient.servicePlace} )`}
                    </Link>
                </li>
            )
        })

        const {search, titleSearch} = this.state
        
        return (
                <Container>
                        <Row justifyContent="md-center">
                            <Col style={infoStyle} col="col-md-9 col-sm-10" >
                                <div className="title_block">
                                    <h1 className="title">{title}</h1>
                                </div>
                                <div style={{
                                    display: 'flex',
                                    justifyContent: 'flex-end'
                                }}>
                                    <Form inline my="2 lg-0">
                                            <Form.Input type="search"
                                                required
                                                id="search"
                                                placeholder="Пошук" 
                                                mr="sm-2"
                                                onChange={this.onChangeSearch}
                                                value={search} 
                                                />

                                            <Button outline primary my="2 sm-0" mr="3" type="button"
                                                onClick={this.handlerClick}
                                                >{`${titleSearch}`}</Button>
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