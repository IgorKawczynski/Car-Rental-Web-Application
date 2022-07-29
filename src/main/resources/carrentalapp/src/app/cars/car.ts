import Big from 'big.js';
export interface Car{
    id: number;
    brand: string;
    model: string;
    engineCapacity: Big;
    bodyTypeEnum: string;
    typeOfFuelEnum: string;
    newCarCost: number;
    productionYear: number;
    pricePerDayRent: number;
    status: string;

    // DO ZMIANY POD NOWĄ LISTĘ KTÓRĄ ROBI ADAM
}
