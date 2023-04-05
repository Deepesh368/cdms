import React from 'react';
import { Container } from '@material-ui/core';

import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login/Login';

const App = () => {
  
    return (
        <BrowserRouter>
            <Container maxWidth="xl">
                <Routes>
                <Route path="/" element={<Login />}>
                </Route>
                </Routes>
            </Container>
        </BrowserRouter>
    );
};

export default App;
