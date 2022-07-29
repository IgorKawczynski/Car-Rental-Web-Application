export class ReservationDto {
    id!: number;
    userId!: number;
    carId!: number;
    brand!: string;
    model!: string;
    dateStart!: Date;
    dateEnd!: Date;
    cost!: number;
    paymentInAdvance!: number;
}
