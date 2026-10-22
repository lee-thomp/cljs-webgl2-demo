;;; Directory Local Variables            -*- no-byte-compile: t -*-
;;; For more information see (info "(emacs) Directory Variables")

((nil . ((eval . (add-hook 'before-save-hook #'delete-trailing-whitespace nil t))
         (indent-tabs-mode . nil)
         (fill-column . 80)))
 (org-mode . ((eval . (auto-fill-mode t)))))
