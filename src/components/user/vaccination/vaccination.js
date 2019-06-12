import React from 'react'
import { Container, Row, Col, Form , 
    Button
 } from 'bootstrap-4-react'

 import MedExApi from '../../medExApi'

import DeatailsList from '../detailsList'

class Vaccination extends React.Component {
    medExApi = new MedExApi()
    constructor(props){
        super(props)
        this.handlerClickAdd = this.handlerClickAdd.bind(this)
        this.handlerChange =  this.handlerChange.bind(this)
        this.getAllVaccination = this.getAllVaccination.bind(this)
        this.handlerBack = this.handlerBack.bind(this)
        this.handlerShowDetails = this.handlerShowDetails.bind(this)
        this.state = {
            data: [],
            addClickBtn: false,
            vaccinationDate: '',
            vaccinaitionName: '',
            vaccinationReaction: '',
            filterData: [],
            childVisible: false
        }
    }

    componentDidMount(){
        this.getAllVaccination()
    }

    async getAllVaccination(){
        let dataAllVacciantion = await this.medExApi
            .getAllVaccination(this.props.idUser)

        this.setState({
            data: [...dataAllVacciantion]
        })

        console.log("Vaccnation" + this.state.data)

    }

    async handlerClickAdd(){
        let personData
        const {vaccinationDate, vaccinaitionName, addClickBtn, vaccinationReaction} = this.state
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

        if(addClickBtn && vaccinationDate.length > 1 && vaccinaitionName.length > 1){
            let dataVaccination = {
                vaccinationDate,
                vaccinaitionName,
                vaccinationReaction,
                patient: personData
            }
            
            let dataNewVaccination = JSON.stringify(dataVaccination)
            console.log(dataNewVaccination)
            await this.medExApi
                .postVaccination(dataNewVaccination)
                this.getAllVaccination()
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
                            {`Дата вакцинації: ${this.medExApi
                                .pasreDate(item.vaccinationDate)}`}
                        </li>
                        <li>
                            {`Назва вакцинації: ${item.vaccinationName}`}
                        </li>
                        <li>
                            {`Реакція на вакцинацію: ${item.vaccinationReaction}`}
                        </li>
                       
                        <Button outline primary className="mt10xp"
                                    onClick={this.handlerBack}>Назад</Button>
                    </ul>
                )
        })

        const formAddAnalisis = () => {
            const {vaccinationDate, vaccinaitionName, vaccinationReaction} = this.state
            if(addClickBtn){
                return(
                    <Form style={formStyle} mb="3" className="shadowBox">
                            <Row justifyContent="md-center display-2">
                                    <p className="tiitleRegistration text-uppercase text-dark font-weight-light">
                                        
                                    </p>
                            </Row>
                            <Row justifyContent="md-center">
                                <Form.Group style={formGroup} mb="3">
                                    <span className="dateTitleForm" display="flex" >Дата вакцинації:</span>
                                    <Form.Input className="text-black"
                                        style={formInputs} lg mt="2" type="date" 
                                        onChange = {this.handlerChange}
                                        id="vaccinationDate"
                                        onChange = {this.handlerChange}
                                        value={vaccinationDate}
                                        disabled/>
                                    
                                    <span className="dateTitleForm" display="flex" >Назва вакцинації:</span>
                                    <Form.Input className="text-black"
                                        style={formInputs} 
                                        lg mt="2" type="text" 
                                        id="vaccinaitionName"
                                        onChange = {this.handlerChange}
                                        value={vaccinaitionName}
                                        disabled/>
                                    
                                    <span className="dateTitleForm" display="flex" >Реакція на вакцинацію:</span>
                                    <textarea rows="5" display="flex" id="vaccinationReaction" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {vaccinationReaction}
                                        onChange = {this.handlerChange} 
                                        placeholder="Опис реакції"/>


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
                            {   this.state.childVisible 
                                ? deatilsForm
                                : <DeatailsList typeList='vaccination' data={this.state.data}
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
                                : <DeatailsList typeList='vaccination' data={this.state.data}
                                onClick={this.handlerShowDetails}/>
                            }
                        </div>
                    )
                    break
            
                case 'doctor':
                        return(
                            <div>
                                {formAddAnalisis()}
                                <Button outline primary className="btn_add" onClick={this.handlerClickAdd}>Додати вакцинацію</Button>
                                    {   this.state.childVisible 
                                        ? deatilsForm
                                        : <DeatailsList typeList='vaccination' data={this.state.data}
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
                                    : <DeatailsList typeList='vaccination' data={this.state.data}
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


export default Vaccination