import { Grid, Button, Toolbar } from "@material-ui/core";
import React, { useState, useEffect } from "react";
import Header from "./Navbar.js";
import { Navigate, useNavigate } from "react-router-dom";
import BasicTable from "./Table.js";

const Securitydashboard = () => {
  return (
    <div>
      <Header />
      <Toolbar />
      <BasicTable />
    </div>
  );
};

export default Securitydashboard;
