import React, { useState } from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import moment from "moment";
import TextField from "@mui/material/TextField";
import { Button } from "@material-ui/core";

import data from "./mock_data.json";

const initialState = {
  orderId: "",
  rollnumber: "",
  deliveryFrom: "",
  deliveryDate: moment().format("D/MM/YYYY"),
  deliveryTime: moment().format("HH:mm"),
  collectedByRollNum: "",
};

const collectState = {
  orderId: "",
  collectedByRollNum: ""
};

const Ordertable = () => {
  const [formData, setFormData] = useState(initialState);
  const [collectData, setcollectData] = useState(collectState);

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

  const handleCollectChange = (e, orderid) => {
    setcollectData({ ...collectData, [e.target.name]: e.target.value, orderId: orderid});
  }
  
  const handleCollectClick = (e) => {
    e.preventDefault();
    console.log(collectData);
  }

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <TextField
          name="orderId"
          type="text"
          label="Order ID"
          variant="outlined"
          sx={{ margin: "1%" }}
          onChange={handleChange}
        />
        <TextField
          name="rollnumber"
          type="text"
          label="Roll Number"
          variant="outlined"
          sx={{ margin: "1%" }}
          onChange={handleChange}
        />
        <TextField
          name="deliveryFrom"
          type="text"
          label="Delivered From"
          variant="outlined"
          sx={{ margin: "1%" }}
          onChange={handleChange}
        />
        <Button
          type="submit"
          color="primary"
          variant="contained"
          style={btnstyle}
        >
          Submit
        </Button>
      </form>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>
                <h3>Order ID</h3>
              </TableCell>
              <TableCell align="right">
                <h3>Roll Number</h3>
              </TableCell>
              <TableCell align="right">
                <h3>Delivered From</h3>
              </TableCell>
              <TableCell align="right">
                <h3>Delivered Date</h3>
              </TableCell>
              <TableCell align="right">
                <h3>Delivered Time</h3>
              </TableCell>
              <TableCell align="right">
                <h3>Collected By</h3>
              </TableCell>
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
                <TableCell align="right">{row.rollnumber}</TableCell>
                <TableCell align="right">{row.deliveryFrom}</TableCell>
                <TableCell align="right">{row.deliveryDate}</TableCell>
                <TableCell align="right">{row.deliveryTime}</TableCell>
                <TableCell align="right">
                  <TextField
                    name="collectedByRollNum"
                    type="text"
                    label="Roll Number"
                    variant="outlined"
                    defaultValue={row.collectedByRollNum}
                    onChange={event => handleCollectChange(event, row.orderId)}
                    sx={{ margin: "1%" }}
                  />
                </TableCell>
                <TableCell align="right">
                  <Button
                    color="primary"
                    variant="contained"
                    style={collectbtnstyle}
                    onClick={handleCollectClick}
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
