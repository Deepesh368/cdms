import React, { useState } from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import moment from "moment";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import { Button } from "@material-ui/core";

import data from "./mock_data.json";

const initialState = {
  orderId: "",
  name: "",
  deliveryFrom: "",
  deliveryDate: moment().format("D/MM/YYYY"),
  deliveryTime: moment().format("HH:mm"),
  collectedByRollNum: "",
  collected: "False",
};

const Ordertable = () => {
  const [details, setDetails] = useState(data);
  const [formData, setFormData] = useState(initialState);

  const btnstyle = {
    backgroundColor: "#20CD51",
    margin: "1.5%",
    width: "150px",
  };

  const collectbtnstyle = {
    backgroundColor: "#3455eb",
    margin: "1.5%",
    width: "150px",
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(formData);
  };

  return (
    <div>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>Order ID</TableCell>
              <TableCell align="right">Name</TableCell>
              <TableCell align="right">Delivered From</TableCell>
              <TableCell align="right">Delivered Date</TableCell>
              <TableCell align="right">Delivered Time</TableCell>
              <TableCell align="right">Collected By</TableCell>
              <TableCell align="right">Collected</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {data.map((row) => (
              <TableRow
                key={row.name}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  {row.orderId}
                </TableCell>
                <TableCell align="right">{row.name}</TableCell>
                <TableCell align="right">{row.deliveryFrom}</TableCell>
                <TableCell align="right">{row.deliveryDate}</TableCell>
                <TableCell align="right">{row.deliveryTime}</TableCell>
                <TableCell align="right">{row.collectedByRollNum}</TableCell>
                <TableCell align="right">{row.collected}</TableCell>
                <TableCell align="right">
                  <Button
                    color="primary"
                    variant="contained"
                    style={collectbtnstyle}
                  >
                    Collect
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default Ordertable;
