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

const Ordertable = () => {
  const collectbtnstyle = {
    backgroundColor: "#3455eb",
    margin: "1.5%",
    width: "150px",
  };

  return (
    <div>
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
                <TableCell align="right">{row.collectedByRollNum}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default Ordertable;
