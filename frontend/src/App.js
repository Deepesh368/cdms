import React from "react";
import { Container } from "@material-ui/core";

import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./components/Login/Login";
import Securitydashboard from "./components/securityDashboard/securityDashboard";

const App = () => {
  return (
    <BrowserRouter>
      <Container maxWidth="xl">
        <Routes>
          <Route path="/" element={<Login />}></Route>
          <Route
            path="/securityDashboard"
            element={<Securitydashboard />}
          ></Route>
        </Routes>
      </Container>
    </BrowserRouter>
  );
};

export default App;
