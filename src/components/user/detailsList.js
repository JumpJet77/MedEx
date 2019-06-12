import React from 'react'
import { Row, Col, Form , 
    Button, InputGroup, Container
 } from 'bootstrap-4-react'
import {Link} from 'react-router-dom'
import '../user/user.css'
import MedExApi from '../medExApi'
 
export default class DetailsList extends React.Component{
    medExApi = new MedExApi()
    render() {
        const {data} = this.props

        const itemAppeal = data.map( (item) => {
            return( <li className="deatailsListItem" id={item.id}  onClick={
                        (e) => {
                            this.props.onClick(e.target.id)
                            console.log("Deatailslist click id: " + e.target.id)
                        }
                    } >{`${this.medExApi.pasreDate(item.appealDate)} "${item.appealEssence}"`}
                </li>
            )
          
        })

        const itemTreatment = data.map( (item) => {
            return( 
                <li className="deatailsListItem">
                    <Link  id={item.id}  onClick={
                        (e) => {
                            this.props.onClick(e.target.id)
                            console.log("Deatailslist click id: " + e.target.id)
                        }
                    } >
                            {`${this.medExApi.pasreDate(item.diagnosisDate)} "${item.diagnosis}"`}
                        </Link>
                </li>
            )
          
        })

        const itemAnalisis = data.map( (item) => {
            return( 
                <li className="deatailsListItem">
                    <Link  id={item.id}  onClick={
                        (e) => {
                            this.props.onClick(e.target.id)
                            console.log("Deatailslist click id: " + e.target.id)
                        }
                    } >
                            {`${this.medExApi.pasreDate(item.analysisDate)} "${item.analysisName}"`}
                        </Link>
                </li>
            )
          
        })

        const itemVaccination = data.map( (item) => {
            return( 
                <li className="deatailsListItem">
                    <Link  id={item.id}  onClick={
                        (e) => {
                            this.props.onClick(e.target.id)
                            console.log("Deatailslist click id: " + e.target.id)
                        }
                    } >
                            {`${this.medExApi.pasreDate(item.vaccinationDate)} "${item.vaccinationName}"`}
                        </Link>
                </li>
            )
          
        })

        const itemDantistExamination = data.map( (item) => {
            return( 
                <li className="deatailsListItem">
                    <Link  id={item.id}  onClick={
                        (e) => {
                            this.props.onClick(e.target.id)
                            console.log("Deatailslist click id: " + e.target.id)
                        }
                    } >
                            {`${this.medExApi.pasreDate(item.dantistExaminationDate)} "${item.diagnosis}"`}
                        </Link>
                </li>
            )
          
        })

        const itemExamination = data.map( (item) => {
            return( 
                <li className="deatailsListItem">
                    <Link  id={item.id}  onClick={
                        (e) => {
                            this.props.onClick(e.target.id)
                            console.log("Deatailslist click id: " + e.target.id)
                        }
                    } >
                            {`${this.medExApi.pasreDate(item.examinationDate)} "Загальне обстеження"`}
                        </Link>
                </li>
            )
          
        })

        const itemPhysicalExamination = data.map( (item) => {
            return( 
                <li className="deatailsListItem">
                    <Link  id={item.id}  onClick={
                        (e) => {
                            this.props.onClick(e.target.id)
                            console.log("Deatailslist click id: " + e.target.id)
                        }
                    } >
                            {`${this.medExApi.pasreDate(item.physicalExaminationDate)} "Загальне обстеження"`}
                        </Link>
                </li>
            )
          
        })

        const showList = () => {
            switch (this.props.typeList) {
                case 'treatment':
                    return itemTreatment
                    break;

                case 'appeal':
                    return itemAppeal
                    break;

                case 'analisis':
                    return itemAnalisis
                    break;

                case 'vaccination':
                    return itemVaccination
                    break;

                case 'dantistExamination':
                    return itemDantistExamination
                    break;

                case 'examination':
                    return itemExamination
                    break;

                case 'physicalExamination':
                    return itemPhysicalExamination
                    break;
            
                default:
                    break;
            }
        } 


        return(
            <div className="deatailsList">
                <ul>
                    {showList()}
                </ul>
            </div>
        )
    }
}