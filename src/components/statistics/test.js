import React, { PureComponent } from 'react';
import {
  ComposedChart, Line, Area, Bar, XAxis, YAxis, CartesianGrid, Tooltip,
  Legend,
} from 'recharts';
import { Container, Row, Col, Form , 
    Button,
 } from 'bootstrap-4-react'

const data1 = [
  {
    name: 'Кір', Хворих: 1, pv: 800, amt: 1400,
  },
  {
    name: 'Краснуха', Хворих: 1, pv: 967, amt: 1506,
  },
  {
    name: 'Дифтерія', Хворих: 2, pv: 1098, amt: 989,
  },
  {
    name: 'Вітряна віспа', Хворих: 6, pv: 1200, amt: 1228,
  },
  {
    name: 'Гепатит А', Хворих: 0, pv: 1108, amt: 1100,
  },
  {
    name: 'ГРВІ', Хворих: 1, pv: 680, amt: 1700,
  },
];

const data2 = [
  {
    name: 'Кір', Хворих: 0, pv: 800, amt: 1400,
  },
  {
    name: 'Краснуха', Хворих: 0, pv: 967, amt: 1506,
  },
  {
    name: 'Дифтерія', Хворих: 0, pv: 1098, amt: 989,
  },
  {
    name: 'Вітряна віспа', Хворих: 0, pv: 1200, amt: 1228,
  },
  {
    name: 'Гепатит А', Хворих: 0, pv: 1108, amt: 1100,
  },
  {
    name: 'ГРВІ', Хворих: 4, pv: 680, amt: 1700,
  },
];


const data3 = [
  {
    name: 'Кір', Хворих: 0, pv: 800, amt: 1400,
  },
  {
    name: 'Краснуха', Хворих: 0, pv: 967, amt: 1506,
  },
  {
    name: 'Дифтерія', Хворих: 0, pv: 1098, amt: 989,
  },
  {
    name: 'Вітряна віспа', Хворих: 0, pv: 1200, amt: 1228,
  },
  {
    name: 'Гепатит А', Хворих: 0, pv: 1108, amt: 1100,
  },
  {
    name: 'ГРВІ', Хворих: 3, pv: 680, amt: 1700,
  },
];


export default class Example extends PureComponent {
  static jsfiddleUrl = 'https://jsfiddle.net/alidingling/94sebfL8/';

  render() {
    const formStyle = {
      marginTop: '15px',
      border: '1px solid #dfdfdf',
      background:'white',
      marginButtom:'50%'
    };

    const doctorGraf = () => {
      return(
        <Form style={formStyle} mb="4" className="shadowBox">
            <Row mt="4" justifyContent="md-center display-2">
              <h1 className="title">Статистика захворюваності підрозділу</h1>
            </Row>
            <Row mt="4" justifyContent="md-center display-2">
                <ComposedChart
                    width={800}
                    height={400}
                    data={data1}
                    margin={{
                      top: 20, right: 20, bottom: 20, left: 20,
                    }}
                  >
                    <CartesianGrid stroke="#f5f5f5" />
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="Хворих" barSize={20} fill="#413ea0" />
                    <Line type="monotone" dataKey="Хворих" stroke="#ff7300" />
                  </ComposedChart>  
            </Row>
        </Form>
      )
    }

    const mainDoctorGraf = () => {
      return(
        <Form style={formStyle} mb="4" className="shadowBox">
          <Row mt="4" style={{
            background: '#FBE06F'
          }} justifyContent="md-center display-2">
              <div style={{
                display: 'flex',
                flexDirection: 'column',
                color: 'red',
                marginBottom: '30px',
                marginTop: '30px'
              }}>
                <h1 className="title">Велика кількість хворих на вітряну віспу на факультеті телекомунікацій, необхідно:</h1>
                <p>Ізолювати хворих</p>
                <p>Здійснювати провітрювання приміщень підрозділу</p>
                <p>Здійснювати вологе прибирання в приміщеннях підрозділу</p>
                <p>Недопустити контакту з хворими</p>
              </div>
            </Row>
          <Row mt="4" justifyContent="md-center display-2">
              <h1 className="title">Статистика захворюваності підрозділів</h1>
            </Row>
            <Row mt="4" justifyContent="md-center display-2">
              <h1 className="title">Факультет Телекомунікацій ( Лікар Ганна Жайворонкова )</h1>
            </Row>
            <Row mt="4" justifyContent="md-center display-2">
                <ComposedChart
                    width={800}
                    height={400}
                    data={data1}
                    margin={{
                      top: 20, right: 20, bottom: 20, left: 20,
                    }}
                  >
                    <CartesianGrid stroke="#f5f5f5" />
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="Хворих" barSize={20} fill="#413ea0" />
                    <Line type="monotone" dataKey="Хворих" stroke="#ff7300" />
                  </ComposedChart>  
            </Row>
            <Row mt="4" justifyContent="md-center display-2">
              <h1 className="title">Факультет Інформаційних Технолгій ( Лікар Артем Чумак )</h1>
            </Row>
            <Row mt="4" justifyContent="md-center display-2">
              <ComposedChart
                width={800}
                height={400}
                data={data2}
                margin={{
                  top: 20, right: 20, bottom: 20, left: 20,
                }}
              >
                <CartesianGrid stroke="#f5f5f5" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="Хворих" barSize={20} fill="#413ea0" />
                <Line type="monotone" dataKey="Хворих" stroke="#ff7300" />
              </ComposedChart>  
            </Row>
            <Row mt="4" justifyContent="md-center display-2">
              <h1 className="title">Факультет Кіберзахисту ( Лікар Ігор Подгайний )</h1>
            </Row>
            <Row mt="4" justifyContent="md-center display-2">
              <ComposedChart
                width={800}
                height={400}
                data={data3}
                margin={{
                  top: 20, right: 20, bottom: 20, left: 20,
                }}
              >
                <CartesianGrid stroke="#f5f5f5" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="Хворих" barSize={20} fill="#413ea0" />
                <Line type="monotone" dataKey="Хворих" stroke="#ff7300" />
              </ComposedChart>  
            </Row> 
        
        </Form>
      )
    }

    const showGraf = () => {
      switch (this.props.role) {
        case 'doctor':
          return doctorGraf()
          break;

        case 'mainDoctor':
          return mainDoctorGraf()
          break;  
      
        default:
          break;
      }
    }
    return (
      <Container style={{background: '#3baeab', minHeight: '800px'}}>
                <Row justifyContent="md-center">
                    <Col  col="col-12" justifyContent="md-center">
                        {showGraf()}
                    </Col>
                </Row>
            </Container>
    );
  }
}
