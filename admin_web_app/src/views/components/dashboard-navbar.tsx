import { useRef, useState } from 'react';
import PropTypes from 'prop-types';
import { AppBar, Avatar, Badge, Box, IconButton, styled, Toolbar, Tooltip, Typography } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import AdminPanelSettingsIcon from '@mui/icons-material/AdminPanelSettings';
import { AccountPopover } from './account-popover';



const DashboardNavbarRoot = styled(AppBar)(({ theme }) => ({
  backgroundColor: theme.palette.background.paper,
  boxShadow: theme.shadows[3]
}));

interface DashboardNavbarProps {
  openSideBar: () => void
}

export const DashboardNavbar = ({ openSideBar }: DashboardNavbarProps) => {
  const settingsRef = useRef(null);
  const [openAccountPopover, setOpenAccountPopover] = useState(false);

  return (
    <>
      <DashboardNavbarRoot
        sx={{
          left: {
            lg: 280
          },
          width: {
            lg: 'calc(100% - 280px)'
          }
        }}
      >
        <Toolbar
          disableGutters
          sx={{
            minHeight: 64,
            left: 0,
            px: 2
          }}
        >
          <IconButton
            onClick={openSideBar}
            sx={{
              display: {
                xs: 'inline-flex',
                lg: 'none'
              }
            }}
          >
            <MenuIcon fontSize="small" />
          </IconButton>
          <Typography
            color="textSecondary"
            variant="h5"
            sx={{ ml: 5 }}
          >
            Achilles Admin Panel
          </Typography>
          <Box sx={{ flexGrow: 1 }} />
          {/* <Tooltip title="Contacts">  
            <IconButton sx={{ ml: 1 }}>
              <AdminPanelSettingsIcon fontSize="small" />
            </IconButton>
          </Tooltip>
          <Tooltip title="Notifications">
            <IconButton sx={{ ml: 1 }}>
              <Badge
                badgeContent={4}
                color="primary"
                variant="dot"
              >
                <AdminPanelSettingsIcon fontSize="small" />
              </Badge>
            </IconButton>
          </Tooltip> */}
          <Avatar
            onClick={() => setOpenAccountPopover(true)}
            ref={settingsRef}
            sx={{
              cursor: 'pointer',
              height: 40,
              width: 40,
              ml: 1
            }}
          >
            <AdminPanelSettingsIcon fontSize="small" />
          </Avatar>
        </Toolbar>
      </DashboardNavbarRoot>
      <AccountPopover
        anchorEl={settingsRef.current}
        open={openAccountPopover}
        onClose={() => setOpenAccountPopover(false)}
      />
    </>
  );
};

DashboardNavbar.propTypes = {
  onSidebarOpen: PropTypes.func
};
