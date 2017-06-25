INSERT INTO public.pessoa(id, ativo, cpf, data_nascimento, email, foto, nome)
SELECT 1, true, '04653183120', '2001-09-28', 'diego@test.com', 'd/c/img.jpg', 'Testador'
	WHERE NOT exists(SELECT * FROM public.pessoa p WHERE p.cpf = '04653183120') limit 1;

