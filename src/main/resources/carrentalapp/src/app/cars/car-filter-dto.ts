import { BodyType } from "./enums/body-type";
import { TypeOfFuel } from "./enums/type-of-fuel";

export class CarFilterDto {
    brand!: string;
    model!: string;
    engineCapacity!: number;
    bodyType!: BodyType | null;
    typeOfFuel!: TypeOfFuel | null;
    productionYear!: number;
    freeFrom!: Date;
    freeTo!: Date;
}
