
import React from 'react'
import { BrowserRouter as Router, Route } from 'react-router-dom'
import MainDoctorMenu from './mainDoctorMenu'
import './mainDoctor.css'
import DataListFaculty from './dataListFuculty'
import Registration from '../registration'
import Test from '../statistics/test'
import MedExApi from '../medExApi'
import DoctorPanel from '../doctor/doctorPanel'

export default class mainDoctorPanel extends React.Component{
    medExApi = new MedExApi()
    constructor(props){
        super(props)
        this.upDateId = this.upDateId.bind(this)
        this.getAllFaculty = this.getAllFaculty.bind(this)
        this.state = {
            dataFacultList: [],
            path: '/'
        }
    }

    async componentDidMount(){
        this.getAllFaculty()
    }

    async getAllFaculty(){
         let data = await this.medExApi
                        .getAllFaculty()

        this.setState({
            dataFacultList: [...data.faculties],
        })
    }


    upDateId(id){
        this.setState({
            idFacult: id
        })
    }

    render(){

        // const upDateId = (id) => {
        //     this.setState({
        //         idUser: id
        //     })

        //     console.log(this.state.idUser)
        // }

        const {idFacult, dataFacultList, path} = this.state
        const {role, logout} = this.props
        return(
            <Router>  
                <div>
                    <MainDoctorMenu role={role} logout={logout}/>
                    <div>

                        <Route path="/" component={() => {
                                return(
                                    <DataListFaculty data={dataFacultList} 
                                        upDateId={this.upDateId}
                                        title="список підрозділів"
                                        path={path}/>
                                )
                            }} exact/>
                        
                       

                        <Route path={`/users`} component={() => {
                                return(
                                    <DoctorPanel
                                        idFacult={this.state.idFacult} 
                                        role={this.props.role}
                                        email={this.props.doctorEmail}/>
                                )
                            }} />

                        <Route path={`/adduser`} component={() => {
                                return(
                                    <Registration/>
                                )
                            }} exact/>  

                        <Route path={`/statistics`} component={() => {
                                return(
                                    <Test role={role}/>
                                )
                            }} exact/>                  
                    </div>
                </div>
            </Router>
        )
    }
}