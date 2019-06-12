



import React, { Component } from 'react'


import LoginForm from './login/loginForm'
import { BrowserRouter as Router, Route } from 'react-router-dom'

class App extends Component {
  render() {
    return (
        <div style={{background: '#3baeab', height: 'auto'}}>
            <Router>
              <LoginForm/>
            </Router>
        </div>
    );
  }
}

export default App;
