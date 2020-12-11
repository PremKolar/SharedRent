# SharedRent
Calculate tenants' rents using different models.


## Models
### Communist Mode

 - Whatever a tenant's income, at the end of the month all tenants will be left with the same residual funds after paying rent, as long as the tenants' living spaces are equal.
 - If the spaces differ, a tenant's rent is weighted by the ratio of their living space to the average tenant's living space. In effect, this leads to the one with the larger room ending up with less money after payday than the one with the smaller room.

### Socialist Mode
 - When my income is twice as big as yours, my rent will be twice as high as yours.
 - If in addition, my living space is half of yours, our rents will be equal.

### Capitalist Mode
 - You pay for what you get. If your living space is 50%, you pay 50% of the rent, no matter how much you earn.



## Definitions
### Living Space
Your room's space plus _1/n'th_ of the shared living space, where n is the total number of tenants.


## Math
https://github.com/PremKolar/SharedRent/blob/main/sharedRentMath.pdf




