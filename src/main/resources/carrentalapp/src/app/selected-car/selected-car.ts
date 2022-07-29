import Big from 'big.js';

export class SelectedCar {
    id!: number;
    brand!: string;
    model!: string;
    engineCapacity!: Big;
    bodyTypeEnum!: string;
    typeOfFuelEnum!: string;
    newCarCost!: number;
    productionYear!: number;
    status!: string;
    pricePerDayRent!: number;
    paymentInAdvance!: number;

}
