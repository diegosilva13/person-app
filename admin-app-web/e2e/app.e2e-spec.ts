import { AdminAppWebPage } from './app.po';

describe('admin-app-web App', () => {
  let page: AdminAppWebPage;

  beforeEach(() => {
    page = new AdminAppWebPage();
  });

  it('should display welcome message', done => {
    page.navigateTo();
    page.getParagraphText()
      .then(msg => expect(msg).toEqual('Welcome to app!!'))
      .then(done, done.fail);
  });
});
