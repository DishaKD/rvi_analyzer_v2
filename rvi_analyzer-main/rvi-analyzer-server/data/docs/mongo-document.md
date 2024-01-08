### User

```json
{
  "user-name": "username",
  "password": "password",
  "password-type": "DEFAULT/PASSWORD/RESET",
  "group": "ADMIN/TOP_ADMIN/USER",
  "status": "ACTIVE/TEMPORARY_BLOCKED/DISABLED",
  "created-by": "user-name-of-created-user",
  "created-date": "date-time",
  "last-updated-date": "some-date"
}
```

### GROUP

```json
{
  "group-id": "id",
  "name": "ADMIN/TOP_ADMIN/USER"
}
```

### Role

```json
{
  "role-id": "id",
  "name": "CREATE_USER/DELETE_USER"
}
```

### GroupRole

```json
{
  "group-id": "id",
  "role-ids": [
    "id-01",
    "id-02"
  ]
}
```

### Report

```json
{
  "created-by": "username",
  "password": "password",
  "mode-type": 1,
  "session-id": "some-session-id",
  "test-id": "some-test-id",
  "url-hash": "some-hash",
  "access-attempts": 0,
  "created-date": "date-time",
  "status": "ACTIVE/DELETED"
}
```

### Device

```json
{
  "created-by": "username",
  "assign-to": [
    "username",
    "username-02"
  ],
  "mac-address": "mac-address-of-device",
  "created-date": "date-time",
  "status": "ACTIVE/TEMPORARY_BLOCKED/DISABLED"
}
```

### Mode 01

```json
{
  "created-by": "username",
  "default-configurations" : {
    "customer-name": "cus-name",
    "operator-id": "operator-id",
    "batch-no": "batch-no",
    "session-id": "session-id"
  },
  "session-configurations" : {
    "voltage": "voltage",
    "max-current": "max-current",
    "pass-min-current": "pass-min-current",
    "pass-max-current": "pass-max-current"
  },
  "results": [
    {
      "test-id": "Test id",
      "readings": [
        {
          "read-at": "date-time",
          "temperature": "temp",
          "current": "current",
          "voltage": "voltage",
          "result": "PASS/FAIL"
        }
      ]
    }
  ],
  "status": "ACTIVE/TEMPORARY_BLOCKED/DISABLED",
  "started-time": "started-time",
  "created-date": "date-time",
  "last-updated-date": "some-date"
}
```


### Mode 02

```json
{
  "created-by": "username",
  "default-configurations" : {
    "customer-name": "cus-name",
    "operator-id": "operator-id",
    "batch-no": "batch-no",
    "session-id": "session-id"
  },
  "session-configurations" : {
    "current": "current",
    "max-voltage": "max-voltage",
    "pass-min-voltage": "pass-min-voltage",
    "pass-max-voltage": "pass-max-voltage"
  },
  "results": [
    {
      "test-id": "Test id",
      "readings": [
        {
          "read-at": "date-time",
          "temperature": "temp",
          "current": "current",
          "voltage": "voltage",
          "result": "PASS/FAIL"
        }
      ]
    }
  ],
  "status": "ACTIVE/TEMPORARY_BLOCKED/DISABLED",
  "started-time": "started-time",
  "created-date": "date-time",
  "last-updated-date": "some-date"
}
```

### Mode 03

```json
{
  "created-by": "username",
  "default-configurations": {
    "customer-name": "cus-name",
    "operator-id": "operator-id",
    "batch-no": "batch-no",
    "session-id": "session-id"
  },
  "session-configurations": {
    "starting-voltage": "Starting voltage",
    "desired-voltage": "Desired Voltage",
    "max-current": "Max current",
    "voltage-resolution": "Voltage resolution",
    "change-in-time": "Change in time"
  },
  "results": [
    {
      "test-id": "Test id",
      "readings": [
        {
          "read-at": "date-time",
          "temperature": "temp",
          "current": "current",
          "voltage": "voltage",
          "result": "PASS/FAIL"
        },
        {
          "read-at": "date-time",
          "temperature": "temp",
          "current": "current",
          "voltage": "voltage",
          "result": "PASS/FAIL"
        }
      ]
    }
  ],
  "status": "ACTIVE/TEMPORARY_BLOCKED/DISABLED",
  "started-time": "started-time",
  "created-date": "date-time",
  "last-updated-date": "some-date"
}
```