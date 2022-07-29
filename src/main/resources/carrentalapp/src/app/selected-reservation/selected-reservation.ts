export class SelectedReservationDto {
    Id!: number;
    userId!: number;
    model!: string;
    brand!: string;
    dateStart!: Date;
    dateEnd!: Date;
    cost!: number;
    paymentInAdvance!: number;
}