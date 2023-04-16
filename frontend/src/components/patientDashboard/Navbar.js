import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { AppBar, Toolbar, Typography, Button } from "@mui/material";
import LocalShippingIcon from "@mui/icons-material/LocalShipping";

const Header = () => {
  let navigate = useNavigate();
  let rollnumber = 'rollnumber'
  const logout = () => {
    return;
  };

  return (
    <React.Fragment>
      <AppBar sx={{ background: "#10BB40", minHeight: "70px" }}>
        <Toolbar>
          <Button
            // color="primary"
            sx={{ color: "white" }}
            size="large"
            startIcon={<LocalShippingIcon />}
            onClick={() => navigate("/")}
          >
            College Delivery Management System
          </Button>
          <Typography
            sx={{
              marginLeft: "auto",
              color: "white",
            }}
          >
            <h3>{rollnumber}</h3>
          </Typography>
          <Button
            sx={{
              "&:hover": { backgroundColor: "#8A0717" },
              marginLeft: "5%",
              borderRadius: "20px",
              background: "red",
              color: "white",
            }}
            variant="contained"
            onClick={logout}
          >
            Log out
          </Button>
        </Toolbar>
      </AppBar>
    </React.Fragment>
  );
};

export default Header;
