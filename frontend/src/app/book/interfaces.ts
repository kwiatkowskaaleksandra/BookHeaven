export interface BookGenreGroup {
  code: string;
  namePL: string;
  nameEN: string;
  active: boolean;
  bookGenreSet: BookGenre[]
}

export interface BookGenre {
  code: string;
  namePL: string;
  nameEN: string;
  active: boolean;
}

