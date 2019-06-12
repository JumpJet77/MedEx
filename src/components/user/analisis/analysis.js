import React from 'react'
import { Container, Row, Col, Form , 
    Button
 } from 'bootstrap-4-react'

 import MedExApi from '../../medExApi'

import DeatailsList from '../detailsList'

class Analysis extends React.Component {
    medExApi = new MedExApi()
    constructor(props){
        super(props)
        this.handlerClickAdd = this.handlerClickAdd.bind(this)
        this.handlerChange =  this.handlerChange.bind(this)
        this.getAllAnalisis = this.getAllAnalisis.bind(this)
        this.handlerBack = this.handlerBack.bind(this)
        this.handlerShowDetails = this.handlerShowDetails.bind(this)
        this.state = {
            data: [],
            addClickBtn: false,
            analysisDate: '',
            analysisName: '',
            analysisResult: '',
            analysisAdditionalComment: '',
            filterData: [],
            childVisible: false
        }
    }

    componentDidMount(){
        this.getAllAnalisis()
        console.log(this.state.data)
    }

    async getAllAnalisis(){
        let dataAllAnalisis = await this.medExApi
            .getAllAnalisis(this.props.idUser)

        this.setState({
            data: [...dataAllAnalisis]
        })

        console.log("Analisis" + this.state.data)

    }

    async handlerClickAdd(){
        let personData
        const {analysisDate, analysisName, addClickBtn, analysisResult, analysisAdditionalComment} = this.state
        this.setState({
            addClickBtn: !this.state.addClickBtn
        })

        this.props.data.map((item) => {
            
            item.person.roles = [{
                id: 5,
                role: "ROLE_USER"
            }]

            personData = item.person
        })

        if(addClickBtn && analysisDate.length > 1 && analysisResult.length > 1){
            let dataAppeal = {
                analysisDate,
                analysisName,
                analysisResult,
                analysisAdditionalComment,
                patient: personData
            }
            
            let dataNewAnalisis = JSON.stringify(dataAppeal)
            console.log(dataNewAnalisis)
            await this.medExApi
                .postAnalisis(dataNewAnalisis)
                this.getAllAnalisis()
        }   
    }

    handlerChange(e){
        const {id} = e.currentTarget
        this.setState({
            [id]: e.currentTarget.value
        })
    }

    handlerBack(){
        this.setState({
            childVisible: !this.state.childVisible
        })
    }

    handlerShowDetails(id){
        
        this.setState(() => {
            let oldData = this.state.data
            let newData = oldData.filter((item) => {
                return item.id == id
            })
            return {
                childVisible: !this.state.childVisible,
                filterData: newData
            }
        })
        
    }

    render() {
        
        const formStyle = {
            marginTop: '2vh',
            border: '1px solid #dfdfdf',
            background:'white'
        }

        const formGroup = {
            width: '90%',

        }

        const formInputs = {
            background: 'white',
        }

        const {addClickBtn, filterData} = this.state
        const {role} = this.props

        const deatilsForm = filterData.map((item) =>{
                console.log(item)
                return(
                    <ul className="deatailsList">
                        <li>
                            {`Дата аналізу: ${this.medExApi
                                .pasreDate(item.analysisDate)}`}
                        </li>
                        <li>
                            {`Назва аналізу: ${item.analysisName}`}
                        </li>
                        <li>
                            {`Результат аналізу: ${item.analysisResult}`}
                        </li>
                        <li>
                            {`Примітки: ${item.analysisAdditionalComment.length > 1
                                            ? item.analysisAdditionalComment
                                            : 'Немає'
                            }`}
                        </li>
                       
                        <Button outline primary className="mt10xp"
                                    onClick={this.handlerBack}>Назад</Button>
                    </ul>
                )
        })

        const formAddAnalisis = () => {
            const {analysisDate, analysisName, analysisResult, analysisAdditionalComment} = this.state
            if(addClickBtn){
                return(
                    <Form style={formStyle} mb="3" className="shadowBox">
                            <Row justifyContent="md-center display-2">
                                    <p className="tiitleRegistration text-uppercase text-dark font-weight-light">
                                        
                                    </p>
                            </Row>
                            <Row justifyContent="md-center">
                                <Form.Group style={formGroup} mb="3">
                                    <span className="dateTitleForm" display="flex" >Дата аналізу:</span>
                                    <Form.Input className="text-black"
                                        style={formInputs} lg mt="2" type="date" 
                                        onChange = {this.handlerChange}
                                        id="analysisDate"
                                        onChange = {this.handlerChange}
                                        value={analysisDate}
                                        disabled/>
                                    
                                    <span className="dateTitleForm" display="flex" >Назва аналізу:</span>
                                    <Form.Input className="text-black"
                                        style={formInputs} 
                                        lg mt="2" type="text" 
                                        id="analysisName"
                                        onChange = {this.handlerChange}
                                        value={analysisName}
                                        disabled/>
                                    
                                    <span className="dateTitleForm" display="flex" >Результат аналізу:</span>
                                    <textarea rows="5" display="flex" id="analysisResult" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {analysisResult}
                                        onChange = {this.handlerChange} 
                                        placeholder="Опис лікування"/>

                                    <span className="dateTitleForm" display="flex" >Примітки:</span>   
                                    <textarea rows="5" display="flex" id="analysisAdditionalComment" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {analysisAdditionalComment}
                                        onChange = {this.handlerChange} 
                                        placeholder="Опис лікування"/>

                                </Form.Group>  
                            </Row>
                    </Form>
                )
            }
        }

        const userForm = () => {
            switch (role) {
                case 'user':
                    return(
                        <div>
                            {   
                                this.state.childVisible 
                                ? deatilsForm
                                : <DeatailsList typeList='analisis' data={this.state.data}
                                onClick={this.handlerShowDetails}/>
                            }
                        </div>
                    )
                    break

                case 'nurse':
                    return(
                        <div>
                            {   this.state.childVisible 
                                ? deatilsForm
                                : <DeatailsList typeList='analisis' data={this.state.data}
                                onClick={this.handlerShowDetails}/>
                            }
                        </div>
                    )
                    break
            
                case 'doctor':
                        return(
                            <div>
                                {formAddAnalisis()}
                                <Button outline primary className="btn_add" onClick={this.handlerClickAdd}>Додати звернення</Button>
                                    {   this.state.childVisible 
                                        ? deatilsForm
                                        : <DeatailsList typeList='analisis' data={this.state.data}
                                        onClick={this.handlerShowDetails}/>
                                    }
                            </div>
                        )
                    break

                case 'mainDoctor':
                        return(
                            <div>
                               {   this.state.childVisible 
                                    ? deatilsForm
                                    : <DeatailsList typeList='analisis' data={this.state.data}
                                    onClick={this.handlerShowDetails}/>
                                }
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


export default Analysis