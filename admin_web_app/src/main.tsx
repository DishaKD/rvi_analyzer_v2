import { CssBaseline, ThemeProvider } from "@mui/material";
import React from "react";
import ReactDOM from "react-dom/client";
import { Provider } from "react-redux";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import App from "./App";

import { store } from "./store/store";
import Login from "./views/auth/login/login";
import PasswordReset from "./views/auth/login/reset-password";
import Dashboard from "./views/dashboard/dashboard";
import QuestionList from "./views/question/QuestionList";
import UserList from "./views/user/view/UserList";
import CustomerList from "./views/customer/view/CustomerList";
import PlantList from "./views/plant/view/PlantList";
import ModeTwoList from "./views/mode_two/mode-two-list";
import ModeThreeList from "./views/mode_three/mode-three-list";
import ModeFourList from "./views/mode_four/mode-four-list";
import ModeFiveList from "./views/mode_five/mode-five-list";
import ModeSixList from "./views/mode_six/mode-six-list";
import { theme } from "./theme";
import ModeOneList from "./views/mode_one/mode-one-list";
import DeviceList from "./views/device/view/DeviceList";
import SignUp from "./views/auth/sign_up/sign_up";

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Provider store={store}>
        <BrowserRouter>
          <App>
            <Routes>
              <Route path="/mode-one" element={<ModeOneList />} />
              <Route path="/mode-two" element={<ModeTwoList />} />
              <Route path="/mode-three" element={<ModeThreeList />} />
              <Route path="/mode-four" element={<ModeFourList />} />
              <Route path="/mode-five" element={<ModeFiveList />} />
              <Route path="/mode-six" element={<ModeSixList />} />
              <Route path="/users" element={<UserList />} />
              <Route path="/plants" element={<PlantList />} />
              <Route path="/customers" element={<CustomerList />} />
              <Route path="/devices" element={<DeviceList />} />
              <Route path="/questions" element={<QuestionList />} />
              <Route path="/login" element={<Login />} />
              <Route path="/password-reset" element={<PasswordReset />} />
              {/* <Route path="/sign-up" element={<SignUp />} /> */}
              <Route path="*" element={<Dashboard />} />
            </Routes>
          </App>
        </BrowserRouter>
      </Provider>
    </ThemeProvider>
  </React.StrictMode>
);
