
import React from 'react'
import { BrowserRouter as Router, Route } from 'react-router-dom'
import MainDoctorMenu from './doctorMenu'
import './mainDoctor.css'
import DataList from './dataList'
import UserCard from '../user/userPanel'
import Registration from '../registration'
import Test from '../statistics/test'
import MedExApi from '../medExApi'

import StoreAppeal from '../storeAppeal'


export default class DoctorPanel extends React.Component{
    medExApi = new MedExApi()
    constructor(props){
        super(props)
        this.upDateId = this.upDateId.bind(this)
        this.getAllPatient = this.getAllPatient.bind(this)
        this.handlerSearchUser = this.handlerSearchUser.bind(this)
        this.state = {
            idUser: '',
            dataUserList: [],
            idFacult: this.props.idFacult
        }
    }

    async componentDidMount(){
        this.getAllPatient(this.props.idFacult)
        let dataPatients = await this.medExApi
            .getDoctorPatients(this.props.email)
            this.setState({
                dataUserList: dataPatients
            })
    }

    async getAllPatient(idFacult){
        let data = await this.medExApi
        .getAllPatient(idFacult)

            this.setState({
                dataUserList: data.patients
            })
    }

    handlerSearchUser(data){

        const {dataUserList} = this.state

        let searchResults = dataUserList.filter( (patient) =>{
            return patient.lastName == data
        } )

        this.setState({
            dataUserList: [...searchResults]
        })
    }

    shouldComponentUpdate(){
        this.forceUpdate()
    }


    upDateId(id){
        this.setState({
            idUser: id
        })
    }

    render(){

        const {dataUserList} = this.state

        let titleUserList = `список військовослужбовців`;

        const {logout, role} = this.props

        let newPath = '/'

        switch (role) {
            case "mainDoctor":
                newPath = '/users'
                break;

            case "nurse":
                newPath = '/users'
                break;
        
            default:
                break;
        }

        return(
            <Router>  
                <div>
                    <MainDoctorMenu role={role} getAllPatient={this.getAllPatient}
                     logout={logout}/>
                    <div>

                        <Route path={`${newPath}`} component={() => {
                                return(
                                    <DataList data={dataUserList} 
                                        upDateId={this.upDateId}
                                        title={titleUserList}
                                        path={newPath}
                                        search={this.handlerSearchUser}/>
                                )
                            }} exact/>
                        
                        {/* <Route path="/users/list" component={() => {
                                return(
                                    <DataList data={dataUserList} 
                                        updateIdFacult={this.updateIdFacult}
                                        title={titleUserList}
                                        path={path}/>
                                )
                            }} exact/>  */}

                        <Route path={`/users/user`} component={() => {
                                return(
                                    <UserCard
                                        email={this.state.idUser} 
                                        role={this.props.role}
                                        doctorEmail={this.props.email}/>
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

                        <Route path={`/store`} component={() => {
                                return(
                                    <StoreAppeal email={this.props.email}/>
                                )
                            }} exact/>                  
                    </div>
                </div>
            </Router>
        )
    }
}


