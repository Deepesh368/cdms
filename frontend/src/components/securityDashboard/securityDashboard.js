import { Toolbar } from "@material-ui/core";
import React, { useEffect } from "react";
import Header from "./Navbar.js";
import { useNavigate } from "react-router-dom";
import BasicTable from "./OrderTable.js";

const Securitydashboard = () => {
  let navigate = useNavigate();

  useEffect(() => {
    if (localStorage.getItem("user-info")) {
      let stored = JSON.parse(localStorage.getItem("user-info"));
      if (stored["uname"] !== "admin") {
        navigate("/studentDashboard");
      }
    } else {
      navigate("/");
    }
  });

  return (
    <div>
      <Header />
      <Toolbar style={{ marginTop: "2%" }} />
      <BasicTable />
    </div>
  );
};

export default Securitydashboard;
