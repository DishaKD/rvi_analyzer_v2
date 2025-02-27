import { useEffect } from 'react';
import PropTypes from 'prop-types';
import { Box, Divider, Drawer, List, ListItem, ListItemButton, ListItemIcon, ListItemText, ListSubheader, useMediaQuery } from '@mui/material';
import LooksOneIcon from '@mui/icons-material/LooksOne';
import LooksTwoIcon from '@mui/icons-material/LooksTwo';
import Looks3Icon from '@mui/icons-material/Looks3';
import Looks4Icon from '@mui/icons-material/Looks4';
import Looks5Icon from '@mui/icons-material/Looks5';
import Looks6Icon from '@mui/icons-material/Looks6';
import DashboardIcon from '@mui/icons-material/Dashboard';
import { NavItem } from './nav-item';
import { Add, Construction, Devices, Group, ListAlt, People, PersonAddAlt } from '@mui/icons-material';

const items = [
  {
    href: '/',
    icon: (<DashboardIcon fontSize="small" />),
    title: 'Dashboard',
    childs: []
  },
  {
    href: '#',
    icon: (<Construction fontSize="small" />),
    title: 'Sessions',
    childs: [
      {
        href: '/mode-one',
        icon: (<LooksOneIcon fontSize="small" />),
        title: 'Mode One',
        childs: []
      },
      {
        href: '/mode-two',
        icon: (<LooksTwoIcon fontSize="small" />),
        title: 'Mode Two',
        childs: []
      },
      {
        href: '/mode-three',
        icon: (<Looks3Icon fontSize="small" />),
        title: 'Mode Three',
        childs: []
      },
      {
        href: '/mode-four',
        icon: (<Looks4Icon fontSize="small" />),
        title: 'Mode Four',
        childs: []
      },
      {
        href: '/mode-five',
        icon: (<Looks5Icon fontSize="small" />),
        title: 'Mode Five',
        childs: []
      },
      {
        href: '/mode-six',
        icon: (<Looks6Icon fontSize="small" />),
        title: 'Mode Six',
        childs: []
      }
    ]
  },
  {
    href: '#',
    icon: (<People fontSize="small" />),
    title: 'Users',
    childs: [
      {
        href: '/add-user',
        icon: (<PersonAddAlt fontSize="small" />),
        title: 'Add',
        childs: []
      },
      {
        href: '/users',
        icon: (<ListAlt fontSize="small" />),
        title: 'List',
        childs: []
      }
    ]
  },
  {
    href: '#',
    icon: (<Devices fontSize="small" />),
    title: 'Device',
    childs: [
      // {
      //   href: '/add-device',
      //   icon: (<Add fontSize="small" />),
      //   title: 'Add',
      //   childs: []
      // },
      {
        href: '/users',
        icon: (<ListAlt fontSize="small" />),
        title: 'List',
        childs: []
      }
    ]
  }
];

type DashboardSidebarData = {
  open: boolean,
  closeSideBar: () => void
}

export const DashboardSidebar = ({ open, closeSideBar }: DashboardSidebarData) => {
  const lgUp = useMediaQuery((theme: any) => theme.breakpoints.up('lg'), {
    defaultMatches: true,
    noSsr: false
  });

  const content = (
    <>
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          height: '100%'
        }}
      >
        <div>
          <Box sx={{ p: 4 }}>

          </Box>

        </div>
        <Divider
          sx={{
            borderColor: '#2D3748',
            my: 3
          }}
        />
        <Box sx={{ flexGrow: 1 }}>
          {items.map((item) => (
            <NavItem
              key={item.title}
              icon={item.icon}
              href={item.href}
              title={item.title}
              childs={item.childs}
            />
          ))}
        </Box>

        <Divider sx={{ borderColor: '#2D3748' }} />
      </Box>
    </>
  );

  if (lgUp) {
    return (
      <Drawer
        anchor="left"
        open
        PaperProps={{
          sx: {
            width: 250,
            borderRightStyle: 'dashed',
            borderColor: "#9d9e9d"
          },
        }}
        variant="permanent"
      >
        {content}
      </Drawer>
    );
  }

  return (
    <Drawer
      anchor="left"
      onClose={closeSideBar}
      open={open}
      PaperProps={{
        sx: {
          width: 250,
          borderRightStyle: 'dashed',
          borderColor: "#9d9e9d"
        },
      }}
      sx={{ zIndex: (theme) => theme.zIndex.appBar + 100 }}
      variant="temporary"
    >
      {content}
    </Drawer>
  );
};

DashboardSidebar.propTypes = {
  onClose: PropTypes.func,
  open: PropTypes.bool
};
