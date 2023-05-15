import { Avatar, Box, Card, CardContent, Grid, Typography } from '@mui/material';
import { ReactNode } from 'react';
import { Link, useNavigate } from 'react-router-dom';

type SummaryCardData = {
  title: string,
  icon: ReactNode,
  value: string,
  path: string,
  color: string
}

export function SummaryCardThin({ title, value, icon, path, color }: SummaryCardData) {
  const navigate = useNavigate();

  return (
    <Link to={path} style={{ textDecoration: 'none' }}>
      <Card
        sx={{
          maxWidth: 1600, backgroundColor: "#FFFFFF", boxShadow: "1px 1px 10px 10px #e8e8e8",
          transition: 'transform 0.3s',
          '&:hover': {
            transform: 'scale(1.02)', // Adjust the scaling factor as desired
          },
        }}
      // onClick={() => navigate(path)}
      >
        <CardContent sx={{ m: 0, p: 0 }}>
          <Grid
            container
            sx={{ justifyContent: 'space-between' }}
          >
            <Typography sx={{ p: 4 }}
              color="grey"
              gutterBottom
              variant="h6"
            >
              {title}
            </Typography>

            <Grid item lg={2} sx={{
              pt: 2,
              justifyContent: 'right'
            }}
              sm={3}
              xl={1}
              xs={2}>
              <Avatar
                sx={{
                  backgroundColor: color,
                  height: 35,
                  width: 35,
                  m: 0,
                }}
              >
                {icon}
              </Avatar>

              <Typography
                sx={{ mt: 0.5, ml: 1, fontWeight: "bold", color: "black" }}
                color="textPrimary"
                variant="h5"
              >
                {value}
              </Typography>
            </Grid>
          </Grid>

        </CardContent>
      </Card>
    </Link>
  );
}