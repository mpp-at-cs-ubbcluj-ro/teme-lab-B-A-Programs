export type AgeGroup = "SIXEIGHT" | "NINEELEVEN" | "TWELVEFIFTEEN";

export interface Trial {
  id?: number;
  name: string;
  ageGroup: AgeGroup;
}