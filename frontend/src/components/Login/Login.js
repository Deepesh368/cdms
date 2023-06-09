import React, { useEffect } from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const theme = createTheme();
let login_url = "http://192.168.49.2:30163/gateway/login";

export default function LogIn() {
  let navigate = useNavigate();

  useEffect(() => {
    axios.get("http://192.168.49.2:30163/gateway/populateData")
    .catch(function(error){console.log(error)});

    if (localStorage.getItem("user-info")) {
      let stored = JSON.parse(localStorage.getItem("user-info"));
      if (stored["uname"] === "admin") {
        navigate("/securityDashboard");
      }
      else {
        navigate("/studentDashboard");
      }
    } else {
      navigate("/");
    }
  }, [navigate]);

  const handleSubmit = (event) => {
    event.preventDefault();
    let data = new FormData(event.currentTarget);

    let item = {
      uname: data.get("rollnumber"),
      pwd: data.get("password"),
    };

    axios
      .post(login_url, item)
      .then(function (response) {
        if (response.data === "Verified") {
          localStorage.setItem("user-info", JSON.stringify(item));
          let stored = JSON.parse(localStorage.getItem("user-info"));
          if (stored["uname"] === "admin") {
            navigate("/securityDashboard");
          }
          else {
            navigate("/studentDashboard");
          }
        } else {
          console.log(response.data);
        }
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="rollnumber"
              label="Roll Number"
              name="rollnumber"
              autoComplete="rollnumber"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign In
            </Button>
            <Grid container>
              <Grid item xs>
                <Link href="#" variant="body2">
                  Forgot password?
                </Link>
              </Grid>
              {/* <Grid item>
                <Link href="#" variant="body2">
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid> */}
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
