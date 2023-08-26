import React from 'react';
import { render } from 'react-dom';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Home from './components/Home';
import Input from './components/Input';
import Login from './components/Login';
import Month from './components/Month';
import Signup from './components/Signup';
import User from './components/User';

function App(){
  return(
    <Router>
      <Routes>
        <Route path="/*" element={<Login/>}/>
        <Route path="/home" element={<Home/>}/>
        <Route path="/input/:monthId" element={<Input/>}/>
        <Route path="/month/:monthid" element={<Month />} />
        <Route path="/signup" element={<Signup/>}/>
        <Route path="/user" element={<User/>}/>
      </Routes>
    </Router>
  );
}

render(<App />, document.getElementById('root'));