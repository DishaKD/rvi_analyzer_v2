import { CssBaseline, ThemeProvider } from '@mui/material'
import React from 'react'
import ReactDOM from 'react-dom/client'
import { Provider } from 'react-redux'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import App from './App'


import { store } from './store/store'
import Login from './views/auth/login/login'
import Dashboard from './views/dashboard/dashboard'
import QuestionList from './views/question/QuestionList'
import UserList from './views/user/view/UserList'
import ModeTwoList from './views/mode_two/mode-two-list'
import ModeThreeList from './views/mode_three/mode-three-list'
import ModeFourList from './views/mode_four/mode-four-list'
import ModeFiveList from './views/mode_five/mode-five-list'
import ModeSixList from './views/mode_six/mode-six-list'
import { theme } from './theme'
import ModeOneList from './views/mode_one/mode-one-list'
import DeviceList from './views/device/view/DeviceList'
import { Pcb_srilanka } from './views/datagrid/pcb_srilanka'
import { Pcb_china } from './views/datagrid/pcb_china'
import { Battery_srilanka } from './views/datagrid/battery_srilanka'
import { Battery_china } from './views/datagrid/battery_china'
import EndLineQcList from './views/softmatter/end_line_qc/end-line-qc-list'

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
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
              <Route path="/devices" element={<DeviceList />} />
              <Route path="/questions" element={<QuestionList />} />
              <Route path="/login" element={<Login />} />
              <Route path="/pcb_srilanka" element={<Pcb_srilanka />} />
              <Route path="/pcb_china" element={<Pcb_china />} />
              <Route path="/battery_srilanka" element={<Battery_srilanka />} />
              <Route path="/battery_china" element={<Battery_china />} />
              <Route path="/end/line/qc" element={<EndLineQcList />} />
              <Route path='*' element={<Dashboard />} />
            </Routes>
          </App>
        </BrowserRouter>
      </Provider>
    </ThemeProvider>
  </React.StrictMode>,
)
