import React, {Component} from 'react'
import GeneralInfo from './generalInfo'
import { BrowserRouter as Router, Route } from 'react-router-dom'
import Appeal from './appeal/appeal'
import MenuUser from './menuUser'
import './user.css'
import Treatment from './appeal/treatment'
import MedExApi from '../medExApi'
import Analysis from './analisis/analysis'
import Vaccination from './vaccination/vaccination'
import DantistExamination from  './examination/dantist'
import Examination from  './examination/examination'
import PhysicalExamination from './examination/physicalExamination'


export default class UserCard extends Component{
    medExApi = new MedExApi()
    constructor(props){
        super(props)
        this.state = {
            data: [],
            dataTreatment: []
        }
    }

    async componentDidMount(){
        const {id, email} = this.props
        let dataPatient = await this.medExApi
        .getPatient(id || email)
        this.setState({
            data: [dataPatient]
        })
    }

    getPerson(data){

    }

    render(){
        const {role, logout} = this.props
        let newPath = '/'
        switch (role) {
            case 'user':
                newPath = '/'
                break;
            
            case 'doctor':
                newPath = '/users/user'
                break;

            case 'mainDoctor':
                newPath = '/users/user'
                break;

            case 'nurse':
                newPath = '/users/user'
                break;
        
            default:
                break;
        }
        return(
            <Router>
                <div className="userCard">
                    <MenuUser role={role} logout={logout}/>  
                    <Route path={`${newPath}`} component={() => {
                        return(
                            <GeneralInfo data={this.state.data}/>
                        )
                    }} exact/> 

                    <Route path={`/analisis`} component={() => {
                        return(
                            <Analysis role={role} idUser={this.props.email} 
                            doctorEmail={this.props.doctorEmail} 
                            data={this.state.data}/>
                        )
                    }} exact/>

                    <Route path={`/vaccination`} component={() => {
                        return(
                            <Vaccination role={role} idUser={this.props.email} 
                            doctorEmail={this.props.doctorEmail} 
                            data={this.state.data}/>
                        )
                    }} exact/>    
                            
                    <Route path="/users/list/user/appeal" component = {() => {
                        return(
                            <Appeal role={role} idUser={this.props.email} 
                                doctorEmail={this.props.doctorEmail} 
                                data={this.state.data}/>
                        )
                    }} exact/>


                    <Route path="/users/list/user/treatment" component = {() => {
                        return(
                            <Treatment role={role} data={this.state.dataTreatment} 
                            idUser={this.props.email}
                            data={this.state.data}/>
                        )
                    }} exact/>

                    <Route path="/user/dantist" component = {() => {
                        return(
                            <DantistExamination role={role} idUser={this.props.email} 
                            doctorEmail={this.props.doctorEmail} 
                            data={this.state.data}/>
                        )
                    }} exact/>


                    <Route path="/user/examination" component = {() => {
                        return(
                            <Examination role={role} idUser={this.props.email} 
                            doctorEmail={this.props.doctorEmail} 
                            data={this.state.data}/>
                        )
                    }} exact/>

                    <Route path="/user/physicalexamination" component = {() => {
                        return(
                            <PhysicalExamination role={role} idUser={this.props.email} 
                            doctorEmail={this.props.doctorEmail} 
                            data={this.state.data}/>
                        )
                    }} exact/>

                    
                </div>
            </Router>
        )
    }
}