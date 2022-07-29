export class ReservationRequestDto {
    email!: string | null;
    carId!: number;
    dateStart!: Date;
    dateEnd!: Date;
}
