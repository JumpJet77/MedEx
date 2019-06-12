
import axios from 'axios'
import swal from 'sweetalert'


export default class MedExApi{
    _apiBase = 'http://139.28.37.150'

    validEmail(data){
        let re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        
        let email = re.test(data)

        return email
    }

    async autorithation(data){
        let token = ''
        
        await axios.post(`${this._apiBase}:8888/login`,data)
            .then(res => {
                return res.data
            })
            .then((data) =>{
                token = data
                return token
            })
            .catch((err) =>{
                swal("Логін або пароль введені не правильно")
            })
        return token
    }

    async getAllFaculty(){
        let data = ''
        await axios.get(`${this._apiBase}:9999/units/0`)
            .then((res) => {
                console.log(res)
                return res.data
            })
            .then((body) => {
                data = body
            })
            return data
    }

    async addPatient(data, id){
        let registationStatus = false

        axios.post(`${this._apiBase}:9999/faculties/${id}/add`,data,
            {
                headers: {
                    'Content-Type' : 'application/json' 
                }
            })
                .then((res) => {
                    if(res.status == 200){
                        swal(`Пацієнт успішно зареєстрований`)
                        registationStatus =true
                    }
                    return registationStatus
                })
    }


    pasreDate(data){
        let date = new Date(data).toLocaleDateString("en-US")
        
        return date
    }

    async logOut(token){
        let logOutStatus = false
        await axios.post(`${this._apiBase}:8888/signout`, token,
        {
            headers: {
                'Content-Type' : 'text/plain' 
            }
        }
        )
            .then((res) => {
                if(res.status == 200){
                    logOutStatus = true
                }                
            })
        return logOutStatus
    }

    async getPatient(data){
        let dataPatien ;
        await axios.get(`${this._apiBase}:9999/patient?email=${data}`)
            .then((res) => {
                return res.data
            })
            .then((body) => {
                dataPatien = body
            })
            
        return dataPatien
    }

    async getDoctorPatients(email){
        let dataPatients = ''
        await axios.get(`${this._apiBase}:9999/doctor/faculty/?email=${email}`)
            .then((res) => {
                console.log()
                return res.data
            })
            .then((body) => {
                dataPatients = body.patients
            })
            console.log(dataPatients)

        return dataPatients
    }

    async getDoctorPatientsAppeals(email){
        let dataPatientsAppeals = ''
        await axios.get(`${this._apiBase}:9999/doctor/?email=${email}`)
            .then((res) => {
                return res.data
            })
            .then((body) => {
                console.log(body.appeals)
                dataPatientsAppeals = body.appeals
            })

        return dataPatientsAppeals
    }

    async getAllPatient(data){
        let dataPatients = ''
        await axios.get(`${this._apiBase}:9999/faculties/${data}`)
            .then((res) => {
                return res.data
            })
            .then((body) => {
                dataPatients = body
            })
        return dataPatients
    }

    async postAppeal(data, email){
        await axios.post(`${this._apiBase}:9999/appeals/add/?email=${email}`, data,
            {
                headers: {
                    'Content-Type' : 'application/json' 
                }
            }
        )
    }

    async postTreatment(data){
        await axios.post(`${this._apiBase}:9999/diagnosis/add/`, data,
            {
                headers: {
                    'Content-Type' : 'application/json' 
                }
            }
        )
    }

    async postAnalisis(data){
        await axios.post(`${this._apiBase}:9999/analysis/add/`, data,
            {
                headers: {
                    'Content-Type' : 'application/json' 
                }
            }
        )
    }

    async postVaccination(data){
        await axios.post(`${this._apiBase}:9999/vaccination/add/`, data,
            {
                headers: {
                    'Content-Type' : 'application/json' 
                }
            }
        )
    }

    async postDantistExamination(data){
        await axios.post(`${this._apiBase}:9999/dantistData/add/`, data,
            {
                headers: {
                    'Content-Type' : 'application/json' 
                }
            }
        )
    }

    async postExamination(data){
        await axios.post(`${this._apiBase}:9999/examinationData/add/`, data,
            {
                headers: {
                    'Content-Type' : 'application/json' 
                }
            }
        )
    }

    async postPhysicalExamination(data){
        await axios.post(`${this._apiBase}:9999/physicalData/add/`, data,
            {
                headers: {
                    'Content-Type' : 'application/json' 
                }
            }
        )
    }

    async getAllAppealId(id){
        let dataAllAppeal = ''
        await axios.get(`${this._apiBase}:9999/appeals/?email=${id}`)
            .then((res) => {
                return res.data
            })
            .then((body) => {
                dataAllAppeal = body 
            })
            
        return dataAllAppeal
    }

    async getAllTreatment(id){
        let dataAllTreatment = ''
        await axios.get(`${this._apiBase}:9999/diagnosis/?email=${id}`)
            .then((res) => {
                console.log(res)
                return res.data
            })
            .then((body) => {
                dataAllTreatment = body 
            })
            
        return dataAllTreatment
    }

    async getAllAnalisis(id){
        let dataAllAnalisis = ''
        await axios.get(`${this._apiBase}:9999/analysis/?email=${id}`)
            .then((res) => {
                console.log(res)
                return res.data
            })
            .then((body) => {
                dataAllAnalisis = body 
            })
            
        return dataAllAnalisis
    }

    async getAllVaccination(id){
        let dataAllVacciantion = ''
        await axios.get(`${this._apiBase}:9999/vaccination/?email=${id}`)
            .then((res) => {
                console.log(res)
                return res.data
            })
            .then((body) => {
                dataAllVacciantion = body 
            })
            
        return dataAllVacciantion
    }

    async getAllDantistExamination(id){
        let dataAllDantistExamination = ''
        await axios.get(`${this._apiBase}:9999/dantistData/?email=${id}`)
            .then((res) => {
                console.log(res)
                return res.data
            })
            .then((body) => {
                dataAllDantistExamination = body 
            })
            
        return dataAllDantistExamination
    }

    async getAllExamination(id){
        let dataAllExamination = ''
        await axios.get(`${this._apiBase}:9999/examinationData/?email=${id}`)
            .then((res) => {
                console.log(res)
                return res.data
            })
            .then((body) => {
                dataAllExamination = body 
            })
            
        return dataAllExamination
    }

    async getAllPhysicalExamination(id){
        let dataAllPhysicalExamination = ''
        await axios.get(`${this._apiBase}:9999/physicalData/?email=${id}`)
            .then((res) => {
                console.log(res)
                return res.data
            })
            .then((body) => {
                dataAllPhysicalExamination = body 
            })
            
        return dataAllPhysicalExamination
    }
    
}