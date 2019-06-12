import React from 'react'
import { Container, Row, Col, Form , 
    Button
 } from 'bootstrap-4-react'

 import MedExApi from '../../medExApi'

import DeatailsList from '../detailsList'

class Examination extends React.Component {
    medExApi = new MedExApi()
    constructor(props){
        super(props)
        this.handlerClickAdd = this.handlerClickAdd.bind(this)
        this.handlerChange =  this.handlerChange.bind(this)
        this.getAllExamination = this.getAllExamination.bind(this)
        this.handlerBack = this.handlerBack.bind(this)
        this.handlerShowDetails = this.handlerShowDetails.bind(this)
        this.state = {
            data: [],
            addClickBtn: false,
            examinationDate: '',
            pulse: '',
            bodyStructure: '',
            bodyCover: '',
            musculoskeletalSystem: '',
            breateOrgans: '',
            bloodCirculation: '',
            bloodPressure: '',
            digestion: '',
            eyesight: '',
            hearing: '',
            neuralSystem: '',
            anotherOrgans: '',
            complaints: '',
            physicalGrowth: '',
            healthState: '',
            treatmentReaction: '',
            badHabits: '',
            specialNotices: '',
            filterData: [],
            childVisible: false
        }
    }

    componentDidMount(){
        this.getAllExamination()
        console.log(this.state.data)
    }

    async getAllExamination(){
        let dataAllExamination = await this.medExApi
            .getAllExamination(this.props.idUser)

        this.setState({
            data: [...dataAllExamination]
        })

    }

    async handlerClickAdd(){
        let personData
        const {examinationDate, pulse, bodyStructure, bodyCover, musculoskeletalSystem,
            anotherOrgans, badHabits, bloodCirculation, bloodPressure, digestion,
           eyesight, healthState, specialNotices, treatmentReaction, complaints,
           neuralSystem, physicalGrowth, hearing,breateOrgans, addClickBtn } = this.state
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

        if(addClickBtn && examinationDate.length > 1 && bodyStructure.length > 1){
            let dataExamination = {
                examinationDate,
                pulse,
                bodyStructure,
                bodyCover,
                musculoskeletalSystem,
                anotherOrgans,
                badHabits,
                bloodCirculation,
                bloodPressure,
                digestion,
                eyesight,
                healthState,
                specialNotices,
                treatmentReaction,
                complaints,
                neuralSystem,
                physicalGrowth,
                hearing,
                breateOrgans,
                patient: personData
            }
            
            let dataNewExamination = JSON.stringify(dataExamination)
            console.log("" + dataNewExamination)
            await this.medExApi
                .postExamination(dataNewExamination)
                this.getAllExamination()
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
                    // <ul className="deatailsList">
                    //     <li>
                    //         {`Дата обстеження: ${this.medExApi
                    //             .pasreDate(item.examinationDate)}`}
                    //     </li>
                    //     <li>
                    //         {`Пульс: ${item.pulse}`}
                    //     </li>
                    //     <li>
                    //         {`Структура тіла: ${item.bodyStructure}`}
                    //     </li>
                    //     <li>
                    //         {`Покрив тіла: ${item.bodyCover}`}
                    //     </li>
                    //     <li>
                    //         {`Мʼязо-скелетна система: ${item.musculoskeletalSystem}`}
                    //     </li>
                    //     <li>
                    //         {`Дихальні органи: ${item.breateOrgans}`}
                    //     </li>
                    //     <li>
                    //         {`Циркуляція крові: ${item.bloodCirculation}`}
                    //     </li>
                    //     <li>
                    //         {`Тиск: ${item.bloodPressure}`}
                    //     </li>
                    //     <li>
                    //         {`Зір: ${item.eyesight}`}
                    //     </li>
                    //     <li>
                    //         {`Слух: ${item.hearing}`}
                    //     </li>
                    //     <li>
                    //         {`Нервова система: ${item.neuralSystem}`}
                    //     </li>
                    //     <li>
                    //         {`Інші органи: ${item.anotherOrgans}`}
                    //     </li>
                    //     <li>
                    //         {`Фізичний розвиток: ${item.physicalGrowth}`}
                    //     </li>
                    //     <li>
                    //         {`Стан здоровʼя: ${item.healthState}`}
                    //     </li>
                    //     <li>
                    //         {`Реакція на лікування: ${item.treatmentReaction}`}
                    //     </li>
                    //     <li>
                    //         {`Погані звички: ${item.badHabits}`}
                    //     </li>
                    //     <li>
                    //         {`Примітки: ${item.specialNotices}`}
                    //     </li>
                        
                       
                    //     <Button outline primary className="mt10xp"
                    //                 onClick={this.handlerBack}>Назад</Button>
                    // </ul>

                    <div>
                            <div style={{
                                width: '100%',
                                display: 'flex',
                                justifyContent: 'center',
                                flexDirection: 'column',
                                alignItems: 'center'
                            }}>
                                <table border="1" style={{
                                    width: '100%'
                                }}>
                                    <tr>
                                        <th>Дата обстеження</th>
                                        <th>Пульс</th>
                                        <th>Структура тіла</th>
                                        <th>Покрив тіла</th>
                                        <th>Мʼязо-скелетна система</th>
                                        <th>Дихальні органи</th>
                                        <th>Циркуляція крові</th>
                                    </tr>
                                    <tr>
                                        <td>{this.medExApi
                                            .pasreDate(item.examinationDate)}</td>
                                        <td>{item.pulse}</td>
                                        <td>{item.bodyStructure}</td>
                                        <td>{item.bodyCover}</td>
                                        <td>{item.musculoskeletalSystem}</td>
                                        <td>{item.breateOrgans}</td>
                                        <td>{item.bloodCirculation}</td>
                                    </tr>
                                </table>

                                <table border="1" style={{
                                    width: '100%',
                                    marginTop: '10px'
                                }}>
                                    <tr>
                                        <th>Тиск</th>
                                        <th>Зір</th>
                                        <th>Слух</th>
                                        <th>Нервова система</th>
                                        <th>Інші органи</th>
                                        <th>Фізичний розвиток</th>
                                    </tr>
                                    <tr>
                                        <td>{item.bloodPressure}</td>
                                        <td>{item.eyesight}</td>
                                        <td>{item.hearing}</td>
                                        <td>{item.neuralSystem}</td>
                                        <td>{item.anotherOrgans}</td>
                                        <td>{item.physicalGrowth}</td>
                                    </tr>
                                </table>

                                <table border="1" style={{
                                    width: '100%',
                                    marginTop: '25px'
                                }}>
                                    <tr>
                                        <th>Стан здоровʼя</th>
                                        <th>Реакція на лікування</th>
                                        <th>Погані звички</th>
                                        <th>Примітки</th>
                                    </tr>
                                    <tr>
                                        <td>{item.healthState}</td>
                                        <td>{item.treatmentReaction}</td>
                                        <td>{item.badHabits}</td>
                                        <td>{item.specialNotices}</td>
                                    </tr>
                                </table>
                            </div>
                            <Button outline primary className="mt10xp"
                                onClick={this.handlerBack}>Назад</Button>
                        </div>
                )
        })

        const formAddAnalisis = () => {
            const {examinationDate, pulse, bodyStructure, bodyCover, musculoskeletalSystem,
             anotherOrgans, badHabits, bloodCirculation, bloodPressure, digestion,
            eyesight, healthState, specialNotices, treatmentReaction, complaints,
            neuralSystem, physicalGrowth, hearing,breateOrgans } = this.state
            if(addClickBtn){
                return(
                    <Form style={formStyle} mb="3" className="shadowBox">
                            <Row justifyContent="md-center display-2">
                                    <p className="tiitleRegistration text-uppercase text-dark font-weight-light">
                                        
                                    </p>
                            </Row>
                            <Row justifyContent="md-center">
                                <Form.Group style={formGroup} mb="3">
                                    <span className="dateTitleForm" display="flex" >Дата обстеження:</span>
                                    <Form.Input className="text-black"
                                        style={formInputs} lg mt="2" type="date" 
                                        onChange = {this.handlerChange}
                                        id="examinationDate"
                                        onChange = {this.handlerChange}
                                        value={examinationDate}
                                        disabled/>
                                    
                                    <span className="dateTitleForm" display="flex" >Пульс:</span>
                                    <Form.Input className="text-black"
                                        style={formInputs} 
                                        lg mt="2" type="text" 
                                        id="pulse"
                                        onChange = {this.handlerChange}
                                        value={pulse}
                                        disabled/>
                                    
                                    <span className="dateTitleForm" display="flex" >Структура тіла:</span>
                                    <textarea rows="3" display="flex" id="bodyStructure" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {bodyStructure}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Покрив тіла:</span>   
                                    <textarea rows="3" display="flex" id="bodyCover" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {bodyCover}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Мʼязо-скелетова система:</span>   
                                    <textarea rows="3" display="flex" id="musculoskeletalSystem" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {musculoskeletalSystem}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Органи дихання:</span>   
                                    <textarea rows="3" display="flex" id="breateOrgans" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {breateOrgans}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Кровообіг:</span>   
                                    <textarea rows="3" display="flex" id="bloodCirculation" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {bloodCirculation}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Тиск:</span>   
                                    <textarea rows="3" display="flex" id="bloodPressure" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {bloodPressure}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Органи травлення:</span>   
                                    <textarea rows="3" display="flex" id="digestion" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {digestion}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>


                                    <span className="dateTitleForm" display="flex" >Зір:</span>   
                                    <textarea rows="3" display="flex" id="eyesight" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {eyesight}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Слух:</span>   
                                    <textarea rows="3" display="flex" id="hearing" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {hearing}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Нервова система:</span>   
                                    <textarea rows="3" display="flex" id="neuralSystem" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {neuralSystem}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Інші органи:</span>   
                                    <textarea rows="3" display="flex" id="anotherOrgans" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {anotherOrgans}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Скарги:</span>   
                                    <textarea rows="3" display="flex" id="complaints" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {complaints}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Фізичний розвиток:</span>   
                                    <textarea rows="3" display="flex" id="physicalGrowth" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {physicalGrowth}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Стан здоровʼя:</span>   
                                    <textarea rows="3" display="flex" id="healthState" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {healthState}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Реакція на лікування:</span>   
                                    <textarea rows="3" display="flex" id="treatmentReaction" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {treatmentReaction}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Погані звички:</span>   
                                    <textarea rows="3" display="flex" id="badHabits" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {badHabits}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

                                    <span className="dateTitleForm" display="flex" >Примітки:</span>   
                                    <textarea rows="3" display="flex" id="specialNotices" 
                                        className="text-black detailsArea field" 
                                        style={formInputs}
                                        lg mt="2"
                                        // required 
                                        value = {specialNotices}
                                        onChange = {this.handlerChange} 
                                        placeholder=""/>

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
                                : <DeatailsList typeList='examination' data={this.state.data}
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
                                : <DeatailsList typeList='examination' data={this.state.data}
                                onClick={this.handlerShowDetails}/>
                            }
                        </div>
                    )
                    break
            
                case 'doctor':
                        return(
                            <div>
                                {formAddAnalisis()}
                                <Button outline primary className="btn_add" onClick={this.handlerClickAdd}>Додати обстеження</Button>
                                    {   this.state.childVisible 
                                        ? deatilsForm
                                        : <DeatailsList typeList='examination' data={this.state.data}
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
                                    : <DeatailsList typeList='examination' data={this.state.data}
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


export default Examination