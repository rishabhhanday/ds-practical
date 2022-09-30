
# quoco-rmi
### Run
**_docker-compose up_** - this will build the project and create a base image that is used to create other docker images of quotations and broker. Used multi-stage dockerfile.

### Arguments for quotation
- args[0] - Remote reference name that will be used to bind the object. For example to bind GirlPower Quotation service, args[0] will be qs-GirlPowerService.
- args[1] - optional argument to get registry from the hostname passed as argument.

### Arguments for broker
- args[0] - Remote reference name that will be used to bind the broker service object. For example args[0] will be bs-BrokerService
- args[1] - Remote reference names of quotation service seperated by comma. For example args[1] = qs-AuldFellasService,qs-GirlPowerService,qs-DodgyDriversService
- args[2] - hostname to get registry from non-local host seperated by comma. For example args[2] = auldfellas,girlpower,dodgydrivers
- args[3] - optional argument to get registry from the hostname passed as argument.

### Arguments for client
- args[0] - broker remote reference name. For example, args[0]=broker
- args[1] - optional argument to get registry from the hostname passed as argument.

**More example around args can be found in docker-compose.yml**


