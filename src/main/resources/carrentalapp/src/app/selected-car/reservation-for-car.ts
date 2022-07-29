import Big from 'big.js';

export interface ReservationForCar {
    userId: number;
    carId: number;
    dateStart: string;
    dateEnd: string;
    cost: Big;
    paymentInAdvance: Big;

}
