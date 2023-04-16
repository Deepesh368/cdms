import { Grid, Button, Toolbar } from "@material-ui/core";
import React, { useState, useEffect } from "react";
import Header from "./Navbar.js";
import { Navigate, useNavigate } from "react-router-dom";
import BasicTable from "./OrderTable.js";

const Patientdashboard = () => {
  return (
    <div>
      <Header />
      <Toolbar style={{marginTop: '2%'}} />
      <BasicTable />
    </div>
  );
};

export default Patientdashboard;
