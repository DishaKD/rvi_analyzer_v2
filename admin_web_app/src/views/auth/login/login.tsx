import { useFormik } from "formik";
import * as Yup from "yup";
import {
  Alert,
  Box,
  Button,
  Container,
  Snackbar,
  TextField,
  Typography,
} from "@mui/material";
import PasswordReset from "./reset-password";
import { useLoginMutation } from "../../../services/login_service";
import { useNavigate } from "react-router-dom";
import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { loginSuccess } from "./auth-slice";
import { alignProperty } from "@mui/material/styles/cssUtils";
import { useGetPOQuery } from "../../../services/po_service";

export default function Login() {
  const [login] = useLoginMutation();
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const dispatch = useDispatch();
  const [isReset, setReset] = useState(false);
  const [failMessage, setFailMessage] = useState("");

  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = (
    event?: React.SyntheticEvent | Event,
    reason?: string
  ) => {
    if (reason === "clickaway") {
      return;
    }

    setOpen(false);
  };

  const formik = useFormik({
    initialValues: {
      userName: "",
      password: "",
    },
    validationSchema: Yup.object({
      userName: Yup.string()
        .email("Must be a valid email")
        .max(255)
        .required("Email is required"),
      password: Yup.string().max(100).required("Password is required"),
    }),
    onSubmit: (values, actions) => {
      login({
        userName: values.userName,
        password: values.password,
        source: "WEB",
      })
        .unwrap()
        .then((payload) => {
          console.log(payload.state);
          localStorage.removeItem("jwt");
          localStorage.setItem("jwt", payload.jwt);
          if (payload.state == "S1000") {
            setReset(false);
            dispatch(loginSuccess(payload));
            localStorage.setItem("user", values.userName);
            navigate("/");
          } else if (payload.state == "S1010") {
            setReset(true);
            navigate("/password-reset");
          } else if (payload.state == "E1200") {
            console.log(payload.state);
            actions.setSubmitting(false);
            actions.resetForm();
            setFailMessage("Un-authorized");
            setOpen(true);
          } else if (payload.state == "E1000") {
            console.log(payload.state);
            actions.setSubmitting(false);
            actions.resetForm();
            setFailMessage("Login failed");
            setOpen(true);
          }
        })
        .catch((error) => {
          actions.setSubmitting(false);
          actions.resetForm();
          setFailMessage("Username or password is incorrect");
          setOpen(true);
        });
    },
  });

  return isReset ? (
    <PasswordReset />
  ) : (
    <Box
      component="main"
      sx={{
        alignItems: "center",
        display: "flex",
        flexGrow: 1,
        minHeight: "100%",
      }}
      style={{ minHeight: "100vh" }}
    >
      <Container maxWidth="sm">
        <form onSubmit={formik.handleSubmit}>
          <Box sx={{ my: 3 }}>
            <Typography color="textPrimary" variant="h4">
              Sign in
            </Typography>
            <Typography color="textSecondary" gutterBottom variant="body2">
              Sign in to the RVI Analyzer admin panel
            </Typography>
          </Box>

          <TextField
            error={Boolean(formik.touched.userName && formik.errors.userName)}
            fullWidth
            helperText={formik.touched.userName && formik.errors.userName}
            label="Email Address"
            margin="normal"
            name="userName"
            onBlur={formik.handleBlur}
            onChange={formik.handleChange}
            type="email"
            value={formik.values.userName}
            variant="outlined"
          />
          <TextField
            error={Boolean(formik.touched.password && formik.errors.password)}
            fullWidth
            helperText={formik.touched.password && formik.errors.password}
            label="Password"
            margin="normal"
            name="password"
            onBlur={formik.handleBlur}
            onChange={formik.handleChange}
            type="password"
            value={formik.values.password}
            variant="outlined"
          />
          <Box sx={{ py: 2 }}>
            <Button
              color="primary"
              disabled={formik.isSubmitting}
              fullWidth
              size="large"
              type="submit"
              variant="contained"
            >
              Sign In
            </Button>
          </Box>
        </form>
      </Container>
      <Snackbar
        open={open}
        autoHideDuration={4000}
        onClose={handleClose}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert onClose={handleClose} severity="error" sx={{ width: "100%" }}>
          {failMessage}
        </Alert>
      </Snackbar>
    </Box>
  );
}
