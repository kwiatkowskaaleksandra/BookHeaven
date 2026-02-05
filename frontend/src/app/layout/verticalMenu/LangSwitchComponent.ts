import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-lang-switch',
  standalone: true,
  template: `
    <a (click)="setLang('pl')" class="lng">
      <img ngSrc='assets/pl.png' alt="Logo" width="20" height="20" style="margin-right: 5px">
    </a>
    <a (click)="setLang('en')" class="lng">
      <img ngSrc='assets/eng.png' alt="Logo" width="20" height="20">
    </a>
  `,
  imports: [
    NgOptimizedImage
  ],
})
export class LangSwitchComponent {
  constructor(private translate: TranslateService) {
    const saved = localStorage.getItem('lang') || 'pl';
    this.translate.use(saved);
    document.documentElement.lang = saved;
  }

  setLang(lang: 'pl' | 'en') {
    this.translate.use(lang);
    localStorage.setItem('lang', lang);
    document.documentElement.lang = lang;
  }
}
